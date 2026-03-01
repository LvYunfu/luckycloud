package org.luckycloud.ai.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luckycloud.ai.config.PromptConfigProperties;
import org.luckycloud.ai.domain.CustomerProfile;
import org.luckycloud.ai.domain.TrainingSession;
import org.luckycloud.ai.service.PromptService;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 提示词管理服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PromptServiceImpl implements PromptService {

    private final PromptConfigProperties promptConfig;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String buildRolePrompt(CustomerProfile profile) {
        if (profile == null) {
            throw new IllegalArgumentException("客户画像不能为空");
        }

        String template = promptConfig.getRolePromptTemplate();
        // 2. 创建 PromptTemplate 对象
        PromptTemplate promptTemplate = new PromptTemplate(template);

        // 3. 准备参数 Map
        Map<String, Object> variables = objectMapper.convertValue(profile, new TypeReference<Map<String, Object>>() {});


        // 4. 渲染模板生成 Prompt 对象
        Prompt prompt = promptTemplate.create(variables);
        return prompt.toString();
    }



    @Override
    public String buildEvaluationPrompt(String promptType, CustomerProfile profile, String aiResponse) {
        if (!StringUtils.hasText(promptType) || profile == null || !StringUtils.hasText(aiResponse)) {
            throw new IllegalArgumentException("测评类型、客户画像和AI回复不能为空");
        }

        String promptTemplate = getEvaluationPromptTemplate(promptType);
        if (!StringUtils.hasText(promptTemplate)) {
            log.warn("未找到测评提示词模板: {}", promptType);
            return buildDefaultEvaluationPrompt(promptType, profile, aiResponse);
        }

        // 根据不同类型的测评替换相应变量
        switch (promptType) {
            case "responseQuality":
                return promptTemplate.replace("{occupation}", profile.getOccupation() != null ? profile.getOccupation() : "")
                                    .replace("{communicationStyle}", profile.getCommunicationStyle() != null ? profile.getCommunicationStyle().getDescription() : "")
                                    .replace("{aiResponse}", aiResponse);
            case "problemSolving":
                String painPoints = profile.getPainPoints() != null ? String.join("、", profile.getPainPoints()) : "";
                return promptTemplate.replace("{painPoints}", painPoints)
                                    .replace("{aiResponse}", aiResponse);
            case "communicationSkill":
                return promptTemplate.replace("{communicationStyle}", profile.getCommunicationStyle() != null ? profile.getCommunicationStyle().getDescription() : "")
                                    .replace("{aiResponse}", aiResponse);
            case "patienceLevel":
                return promptTemplate.replace("{difficultyLevel}", profile.getDifficultyLevel() != null ? profile.getDifficultyLevel().toString() : "5")
                                    .replace("{aiResponse}", aiResponse);
            case "overallPerformance":
                String profileSummary = buildProfileSummary(profile);
                return promptTemplate.replace("{profileSummary}", profileSummary)
                                    .replace("{aiResponse}", aiResponse);
            case "detailedFeedback":
                profileSummary = buildProfileSummary(profile);
                return promptTemplate.replace("{profileSummary}", profileSummary)
                                    .replace("{aiResponse}", aiResponse);
            default:
                log.warn("未知的测评类型: {}", promptType);
                return buildDefaultEvaluationPrompt(promptType, profile, aiResponse);
        }
    }





    /**
     * 获取测评提示词模板
     */
    private String getEvaluationPromptTemplate(String promptType) {
        PromptConfigProperties.EvaluationPrompts evaluation = promptConfig.getEvaluation();
        if (evaluation == null) {
            return null;
        }

        switch (promptType) {
            case "responseQuality":
                return evaluation.getResponseQualityPrompt();
            case "problemSolving":
                return evaluation.getProblemSolvingPrompt();
            case "communicationSkill":
                return evaluation.getCommunicationSkillPrompt();
            case "patienceLevel":
                return evaluation.getPatienceLevelPrompt();
            case "overallPerformance":
                return evaluation.getOverallPerformancePrompt();
            case "detailedFeedback":
                return evaluation.getDetailedFeedbackPrompt();
            default:
                return null;
        }
    }

    /**
     * 构建默认测评提示词
     */
    private String buildDefaultEvaluationPrompt(String promptType, CustomerProfile profile, String aiResponse) {
        switch (promptType) {
            case "responseQuality":
                return String.format("评估以下回复的质量（满分100分）：\n客户类型：%s\n沟通风格：%s\n回复内容：%s\n评估标准：相关性、准确性、完整性\n请返回格式：分数|反馈理由",
                        profile.getOccupation(), profile.getCommunicationStyle().getDescription(), aiResponse);
            case "problemSolving":
                String painPoints = profile.getPainPoints() != null ? String.join("、", profile.getPainPoints()) : "";
                return String.format("评估以下回复的问题解决能力（满分100分）：\n客户痛点：%s\n回复内容：%s\n评估标准：针对性、实用性、创新性\n请返回格式：分数|反馈理由",
                        painPoints, aiResponse);
            case "communicationSkill":
                return String.format("评估以下回复的沟通技巧（满分100分）：\n客户沟通风格：%s\n回复内容：%s\n评估标准：适应性、亲和力、说服力\n请返回格式：分数|反馈理由",
                        profile.getCommunicationStyle().getDescription(), aiResponse);
            case "patienceLevel":
                return String.format("评估以下回复的耐心程度（满分100分）：\n客户刁难程度：%d/10\n回复内容：%s\n评估标准：容忍度、回应态度、情绪控制\n请返回格式：分数|反馈理由",
                        profile.getDifficultyLevel(), aiResponse);
            default:
                return String.format("评估以下AI回复：%s\n回复内容：%s", promptType, aiResponse);
        }
    }

    /**
     * 构建客户画像摘要
     */
    private String buildProfileSummary(CustomerProfile profile) {
        return String.format("%s，%d岁%s，%s，收入水平%s，沟通风格%s，刁难程度%d/10",
                profile.getCustomerName(),
                profile.getAge(),
                profile.getGender(),
                profile.getOccupation(),
                profile.getIncomeLevel().getDescription(),
                profile.getCommunicationStyle().getDescription(),
                profile.getDifficultyLevel());
    }
}
