package org.luckycloud.ai.config;

import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 提示词配置属性类
 */
@Data
@Component
@ConfigurationProperties(prefix = "ai.prompts")
public class PromptConfigProperties {

    @Resource
    Environment environment;
    /**
     * 角色设定提示词模板
     */
    private String rolePromptTemplate;


    /**
     * 上下文提示词模板
     */
    private String contextPromptTemplate;

    /**
     * 对话历史项模板
     */
    private String conversationHistoryItemTemplate;

    /**
     * 测评相关提示词
     */
    private EvaluationPrompts evaluation;

    @Data
    public static class EvaluationPrompts {
        private String roleConsistencyPrompt;
        private String responseQualityPrompt;
        private String problemSolvingPrompt;
        private String communicationSkillPrompt;
        private String patienceLevelPrompt;
        private String overallPerformancePrompt;
        private String detailedFeedbackPrompt;
    }

}
