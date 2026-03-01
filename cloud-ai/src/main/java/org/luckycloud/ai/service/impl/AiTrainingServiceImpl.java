package org.luckycloud.ai.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.luckycloud.ai.domain.CustomerProfile;
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
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
    public TrainingResponse startNewSession(TrainingRequest request) {
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

            // 初始化AI对话
            String initialResponse = generateInitialResponse(prompt, request.getUserMessage());

            // 添加初始消息到会话
            session.addUserMessage(request.getUserMessage());
            session.addAiMessage(initialResponse);

            // 存储会话
            sessionMap.put(session.getSessionId(), session);

            log.info("成功创建新的培训会话，sessionId: {}", session.getSessionId());

            return TrainingResponse.success(session.getSessionId(), initialResponse);

        } catch (Exception e) {
            log.error("创建培训会话失败", e);
            return TrainingResponse.error("创建会话失败: " + e.getMessage());
        }
    }

    @Override
    public TrainingResponse continueSession(TrainingSessionRequest sessionRequest) {
        String sessionId = sessionRequest.getSessionId();
        try {
            // 验证会话是否存在
            TrainingSession session = sessionMap.get(sessionId);
            if (session == null) {
                return TrainingResponse.error("会话不存在或已过期");
            }

            if (session.getStatus() != TrainingSession.SessionStatus.ACTIVE) {
                return TrainingResponse.error("会话已结束或已暂停");
            }

            // 添加用户消息
            session.addUserMessage(sessionRequest.getUserMessage());

            // 获取客户画像
            CustomerProfile profile = customerProfileService.getProfileById(session.getProfileId());

            // 使用提示词服务构建上下文提示词
            String contextPrompt = promptService.buildRolePrompt(profile);

            // 生成AI回复
            String aiResponse = generateResponse(contextPrompt, sessionRequest.getUserMessage(), session.getMessages());

            // 添加AI回复
            session.addAiMessage(aiResponse);

            // 执行测评（如果需要）
            List<org.luckycloud.ai.domain.EvaluationResult> evaluations = new ArrayList<>();
            if (Boolean.TRUE.equals(sessionRequest.getNeedEvaluation())) {
                evaluations = evaluationService.evaluateResponse(session, aiResponse);
                session.getEvaluations().addAll(evaluations);
            }

            log.info("会话继续成功，sessionId: {}", sessionId);

            if (!evaluations.isEmpty()) {
                return TrainingResponse.successWithEvaluation(sessionId, aiResponse, evaluations);
            } else {
                return TrainingResponse.success(sessionId, aiResponse);
            }

        } catch (Exception e) {
            log.error("继续会话失败，sessionId: {}", sessionId, e);
            return TrainingResponse.error("继续会话失败: " + e.getMessage());
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
        return  customerProfileService.getProfileById("difficult_customer_001");


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
    private String generateInitialResponse(String rolePrompt, String userMessage) {
        String fullPrompt = rolePrompt + "\n\n现在开始对话，用户的开场白是：" + userMessage;
        return callAiModel(fullPrompt,new ArrayList<>());
    }

    /**
     * 生成回复
     */
    private String generateResponse(String contextPrompt, String userMessage,List<Message> history) {
        String fullPrompt = contextPrompt + "\n用户说：" + userMessage + "\n请以客户身份回复：";
        return callAiModel(fullPrompt,history);
    }

    /**
     * 调用AI模型
     */
    private String callAiModel(String prompt, List<Message> history) {
        try {
            return chatClient.prompt()
                    .user(prompt)
                    .messages(history)
                    .call()
                    .content();
        } catch (Exception e) {
            log.error("调用AI模型失败", e);
            return "抱歉，我现在无法回复，请稍后再试。";
        }
    }

}
