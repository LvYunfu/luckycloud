package org.luckycloud.ai.service;

import org.luckycloud.ai.domain.CustomerProfile;
import org.luckycloud.ai.domain.TrainingSession;
import org.luckycloud.ai.dto.TrainingRequest;
import org.luckycloud.ai.dto.TrainingResponse;
import org.luckycloud.ai.dto.TrainingSessionRequest;
import org.luckycloud.ai.enums.AiModelEnum;
import java.util.List;

/**
 * AI陪练服务接口
 */
public interface AiTrainingService {

    /**
     * 开始新的培训会话
     */
    TrainingResponse startNewSession(TrainingRequest request);

    /**
     * 继续现有会话
     */
    TrainingResponse continueSession(TrainingSessionRequest sessionRequest);

    /**
     * 结束会话
     */
    void endSession(String sessionId);

    /**
     * 获取会话详情
     */
    TrainingSession getSession(String sessionId);

    /**
     * 获取用户的所有会话
     */
    List<TrainingSession> getUserSessions(String userId);



    /**
     * 获取所有可用的AI模型
     */
    List<AiModelEnum> getAvailableModels();

    /**
     * 切换会话中的AI模型
     */
    boolean switchAiModel(String sessionId, AiModelEnum newModel);
}
