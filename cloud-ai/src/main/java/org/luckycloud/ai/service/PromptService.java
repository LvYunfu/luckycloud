package org.luckycloud.ai.service;

import org.luckycloud.ai.domain.CustomerProfile;
import org.luckycloud.ai.domain.TrainingSession;
import org.springframework.ai.chat.prompt.Prompt;

/**
 * 提示词管理服务接口
 */
public interface PromptService {

    /**
     * 构建角色设定提示词
     * @param profile 客户画像
     * @return 角色设定提示词
     */
    String buildRolePrompt(CustomerProfile profile);



    /**
     * 构建测评提示词
     * @param promptType 测评类型
     * @param profile 客户画像
     * @param aiResponse AI回复内容
     * @return 测评提示词
     */
    String buildEvaluationPrompt(String promptType, CustomerProfile profile, String aiResponse);
}
