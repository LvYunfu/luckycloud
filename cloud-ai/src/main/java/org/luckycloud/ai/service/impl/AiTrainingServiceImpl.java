package org.luckycloud.ai.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.luckycloud.ai.domain.CustomerProfile;
import org.luckycloud.ai.domain.EvaluationResult;
import org.luckycloud.ai.domain.TrainingSession;
import org.luckycloud.ai.dto.TrainingRequest;
import org.luckycloud.ai.dto.TrainingResponse;
import org.luckycloud.ai.dto.TrainingSessionRequest;
import org.luckycloud.ai.enums.AiModelEnum;
import org.luckycloud.ai.service.AiTrainingService;
import org.luckycloud.ai.service.CustomerProfileService;
import org.luckycloud.ai.service.EvaluationService;
import org.luckycloud.ai.service.PromptService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * AI陪练服务实现类
 */
@Slf4j
@Service
public class AiTrainingServiceImpl implements AiTrainingService {

    @Resource
    private ChatClient chatClient;

    @Resource
    private EvaluationService evaluationService;

    @Resource
    private PromptService promptService;

    @Resource
    private CustomerProfileService customerProfileService;

    // 内存存储会话数据（生产环境应使用Redis等）
    private final Map<String, TrainingSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public Flux<TrainingResponse> startNewSession(TrainingRequest request) {
        try {

            // 获取或创建客户画像
            CustomerProfile profile = getOrCreateCustomerProfile(request.getCustomerId());
            AiModelEnum modelEnum = AiModelEnum.getByModelId(request.getModel());

            // 创建新的培训会话
            TrainingSession session = new TrainingSession(
                    request.getUserId(),
                    profile.getProfileId(),
                    modelEnum
            );

            // 使用提示词服务构建角色设定提示词
            String prompt = promptService.buildRolePrompt(profile);
            log.info("成功创建新的培训会话，sessionId: {}", session.getSessionId());
            // 添加初始消息到会话
            session.addUserMessage(request.getUserMessage());

            // 存储会话
            sessionMap.put(session.getSessionId(), session);

            // 初始化AI对话
            return generateInitialResponse(prompt, request.getUserMessage(), session);


        } catch (Exception e) {
            log.error("创建培训会话失败", e);
            return Flux.just(TrainingResponse.error("创建会话失败: " + e.getMessage()));
        }
    }

    @Override
    public Flux<TrainingResponse> continueSession(TrainingSessionRequest sessionRequest) {
        String sessionId = sessionRequest.getSessionId();
        try {
            // 验证会话是否存在
            TrainingSession session = sessionMap.get(sessionId);
            if (session == null) {
                return Flux.just(TrainingResponse.error("会话不存在或已过期"));
            }

            if (session.getStatus() != TrainingSession.SessionStatus.ACTIVE) {
                return Flux.just(TrainingResponse.error("会话已结束或已暂停"));
            }

            // 添加用户消息
            session.addUserMessage(sessionRequest.getUserMessage());

            // 获取客户画像
            CustomerProfile profile = customerProfileService.getProfileById(session.getProfileId());

            // 使用提示词服务构建上下文提示词
            String contextPrompt = promptService.buildRolePrompt(profile);
            log.info("会话继续成功，sessionId: {}", sessionId);

            // 生成AI回复
            return generateResponse(contextPrompt, sessionRequest.getUserMessage(), session);


        } catch (Exception e) {
            log.error("继续会话失败，sessionId: {}", sessionId, e);
            return Flux.just(TrainingResponse.error("继续会话失败: " + e.getMessage()));
        }
    }

    @Override
    public void endSession(String sessionId) {
        TrainingSession session = sessionMap.get(sessionId);
        if (session != null) {
            session.endSession();
            log.info("会话已结束，sessionId: {}", sessionId);
        }
    }

    @Override
    public TrainingSession getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }

    @Override
    public List<TrainingSession> getUserSessions(String userId) {
        return sessionMap.values().stream()
                .filter(session -> userId.equals(session.getUserId()))
                .toList();
    }

    /**
     * 获取或创建客户画像
     */
    private CustomerProfile getOrCreateCustomerProfile(String customerId) {
        customerId = ObjectUtils.isEmpty(customerId) ? "difficult_customer_001" : customerId;
        CustomerProfile existingProfile = customerProfileService.getProfileById(customerId);
        if (existingProfile != null) {
            return existingProfile;
        }
        return customerProfileService.getProfileById("difficult_customer_001");


    }

    @Override
    public List<AiModelEnum> getAvailableModels() {
        return Arrays.asList(AiModelEnum.values());
    }

    @Override
    public boolean switchAiModel(String sessionId, AiModelEnum newModel) {
        TrainingSession session = sessionMap.get(sessionId);
        if (session != null && session.getStatus() == TrainingSession.SessionStatus.ACTIVE) {
            session.setAiModel(newModel);
            log.info("会话模型已切换，sessionId: {}, newModel: {}", sessionId, newModel.getModelName());
            return true;
        }
        return false;
    }

    /**
     * 生成初始回复
     */
    private Flux<TrainingResponse> generateInitialResponse(String rolePrompt, String userMessage, TrainingSession session) {
        String fullPrompt = rolePrompt + "\n\n现在开始对话，用户的开场白是：" + userMessage;
        return callAiModel(fullPrompt, session);
    }

    /**
     * 生成回复
     */
    private Flux<TrainingResponse> generateResponse(String contextPrompt, String userMessage, TrainingSession session) {
        String fullPrompt = contextPrompt + "\n用户说：" + userMessage + "\n请以客户身份回复：";
        return callAiModel(fullPrompt, session);
    }

    /**
     * 调用AI模型
     */
    private Flux<TrainingResponse> callAiModel(String prompt, TrainingSession session) {
        AtomicReference<StringBuilder> contentBuilder = new AtomicReference<>(new StringBuilder());

        try {
            //mock 数据 两个字符发送一次
            String mockResponse = "你好，我上周在你们旗舰店买的智能落地灯，收到货用了不到三天就坏了，完全不亮了，这质量也太差了吧！";
            Flux<TrainingResponse> originalFlux = Flux.fromArray(mockResponse.split("(?<=\\G.{2})")) // 每两个字符分割一次
                    .delayElements(java.time.Duration.ofMillis(200)) // 模拟每个分段的延迟
                    .map(content -> {
                        contentBuilder.get().append(content);
                        return TrainingResponse.success(session.getSessionId(), content);
                    });

//
//            Flux<TrainingResponse> originalFlux = chatClient.prompt()
//                    .user(prompt)
//                    .messages(session.getMessages())
//                    .stream()
//                    .content()
//                    .doOnNext(e -> contentBuilder.get().append(e))
//                    .map(content -> TrainingResponse.success(session.getSessionId(), content));
            // 3. 定义测评并追加内容的逻辑
            Mono<Flux<TrainingResponse>> extraFluxMono = Mono.fromRunnable(() -> {
                        session.addAiMessage(contentBuilder.get().toString());

                        // 执行角色一致性测评
//                            Integer roleConsistency = evaluationService.evaluateRoleConsistency(session, contentBuilder.get().toString());
                        Integer roleConsistency = 50;
                        // 得分<80时，需要追加内容
                        if (roleConsistency < 80) {
                            // 记录需要追加的内容（用ThreadLocal或直接传递）
                            contentBuilder.get().append("追加的内容");
                        }

                    })
                    .subscribeOn(Schedulers.boundedElastic())
                    .then(Mono.defer(() -> {
                        // 判断是否需要追加内容

//                        Integer roleConsistency = evaluationService.evaluateRoleConsistency(session, contentBuilder.get().toString());
                        Integer roleConsistency = 50;
                        if (roleConsistency < 80) {
                            // 流式输出追加的内容（逐字符/逐段，保持流式特性）
                            return Mono.just(Flux.fromArray("追加的内容".split("")) // 按字符拆分实现流式
                                    .map(charContent -> TrainingResponse.success(session.getSessionId(), charContent)));
                        }

                        // 不需要追加时，返回空流
                        return Mono.just(Flux.empty());
                    }));
            Flux<TrainingResponse> ff = Flux.create(sink -> {
                System.out.println("asd");
                sink.next(TrainingResponse.success("", ""));
                sink.complete();
            });
            // 4. 拼接原流 + 追加流（保证顺序执行）
            return originalFlux.concatWith(extraFluxMono.flatMapMany(flux -> flux)).concatWith(ff);
        } catch (Exception e) {
            log.error("调用AI模型失败", e);
            return Flux.just(TrainingResponse.error("抱歉，我现在无法回复，请稍后再试。"));
        }
    }


}
