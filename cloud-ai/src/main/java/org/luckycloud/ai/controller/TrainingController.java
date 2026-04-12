package org.luckycloud.ai.controller;

import lombok.extern.slf4j.Slf4j;
import org.luckycloud.ai.dto.TrainingRequest;
import org.luckycloud.ai.dto.TrainingResponse;
import org.luckycloud.ai.dto.TrainingSessionRequest;
import org.luckycloud.ai.enums.AiModelEnum;
import org.luckycloud.ai.service.AiTrainingService;
import org.luckycloud.dto.common.Response;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;

/**
 * AI陪练控制器
 */
@Slf4j
@RestController
@RequestMapping("/ai/training")
public class TrainingController {

    @Resource
    private AiTrainingService aiTrainingService;

    /**
     * 开始新的培训会话
     */
    @PostMapping(path = "/start", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response<TrainingResponse>> startNewSession(@RequestBody TrainingRequest request) {
        log.info("收到开始新会话请求，userId: {}", request.getUserId());
        return aiTrainingService.startNewSession(request).map(Response::successData);
    }

    /**
     * 继续现有会话
     */
    @PostMapping(path = "/continue", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response<TrainingResponse>> continueSession(@RequestBody TrainingSessionRequest request) {

        log.info("收到继续会话请求，sessionId: {}", request.getSessionId());
        return aiTrainingService.continueSession(request).map(Response::successData);
    }

    /**
     * 结束会话
     */
    @PostMapping("/end")
    public void endSession(String sessionId) {
        log.info("收到结束会话请求，sessionId: {}", sessionId);
        aiTrainingService.endSession(sessionId);
    }

    /**
     * 获取会话详情
     */
    @GetMapping("/session")
    public Object getSession(String sessionId) {
        log.info("收到获取会话详情请求，sessionId: {}", sessionId);
        return aiTrainingService.getSession(sessionId);
    }

    /**
     * 获取用户的所有会话
     */
    @GetMapping("/sessions/user")
    public List<?> getUserSessions(String userId) {
        log.info("收到获取用户会话列表请求，userId: {}", userId);
        return aiTrainingService.getUserSessions(userId);
    }

    /**
     * 获取所有可用的AI模型
     */
    @GetMapping("/models")
    public List<AiModelEnum> getAvailableModels() {
        return aiTrainingService.getAvailableModels();
    }

    /**
     * 切换会话中的AI模型
     */
    @PostMapping("/switch-model/{sessionId}")
    public boolean switchAiModel(
            @PathVariable String sessionId,
            @RequestParam String modelId) {

        try {
            AiModelEnum modelEnum = AiModelEnum.getByModelId(modelId);
            log.info("收到切换模型请求，sessionId: {}, model: {}", sessionId, modelId);
            return aiTrainingService.switchAiModel(sessionId, modelEnum);
        } catch (IllegalArgumentException e) {
            log.error("无效的模型ID: {}", modelId);
            return false;
        }
    }

    /**

    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    public String healthCheck() {
        return "AI陪练服务运行正常";
    }
}
