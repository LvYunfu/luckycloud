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

import java.util.HashMap;
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
        return buildPrompt(template, profile);
    }

    @Override
    public <T> String buildPrompt(String template, T params) {
        // 2. 创建 PromptTemplate 对象
        PromptTemplate promptTemplate = new PromptTemplate(template);
        // 3. 准备参数 Map
        Map<String, Object> variables = objectMapper.convertValue(params, new TypeReference<>() {
        });
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

        PromptTemplate template = new PromptTemplate(promptTemplate);


        // 根据不同类型的测评替换相应变量
        return switch (promptType) {
            case "roleConsistency" -> buildRoleConsistencyPrompt(template, profile, aiResponse);
            case "responseQuality" ->
                    promptTemplate.replace("{occupation}", profile.getOccupation() != null ? profile.getOccupation() : "")
                            .replace("{communicationStyle}", profile.getCommunicationStyle() != null ? profile.getCommunicationStyle().getDescription() : "")
                            .replace("{aiResponse}", aiResponse);
            case "problemSolving" -> {
                String painPoints = profile.getPainPoints() != null ? String.join("、", profile.getPainPoints()) : "";
                yield promptTemplate.replace("{painPoints}", painPoints)
                        .replace("{aiResponse}", aiResponse);
            }
            case "communicationSkill" ->
                    promptTemplate.replace("{communicationStyle}", profile.getCommunicationStyle() != null ? profile.getCommunicationStyle().getDescription() : "")
                            .replace("{aiResponse}", aiResponse);
            case "patienceLevel" ->
                    promptTemplate.replace("{difficultyLevel}", profile.getDifficultyLevel() != null ? profile.getDifficultyLevel().toString() : "5")
                            .replace("{aiResponse}", aiResponse);
            case "overallPerformance", "detailedFeedback" -> {
                String profileSummary = buildProfileSummary(profile);
                yield promptTemplate.replace("{profileSummary}", profileSummary)
                        .replace("{aiResponse}", aiResponse);
            }
            default -> {
                log.warn("未知的测评类型: {}", promptType);
                yield "";
            }
        };
    }


    /**
     * 获取测评提示词模板
     */
    private String getEvaluationPromptTemplate(String promptType) {
        PromptConfigProperties.EvaluationPrompts evaluation = promptConfig.getEvaluation();
        if (evaluation == null) {
            return null;
        }

        return switch (promptType) {
            case "roleConsistency" -> evaluation.getRoleConsistencyPrompt();
            case "responseQuality" -> evaluation.getResponseQualityPrompt();
            case "problemSolving" -> evaluation.getProblemSolvingPrompt();
            case "communicationSkill" -> evaluation.getCommunicationSkillPrompt();
            case "patienceLevel" -> evaluation.getPatienceLevelPrompt();
            case "overallPerformance" -> evaluation.getOverallPerformancePrompt();
            case "detailedFeedback" -> evaluation.getDetailedFeedbackPrompt();
            default -> null;
        };
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

    private String buildRoleConsistencyPrompt(PromptTemplate template, CustomerProfile profile, String aiResponse) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("profile", buildProfileSummary(profile));
        variables.put("aiResponse", aiResponse);
        Prompt prompt = template.create(variables);
        return prompt.toString();
    }
}
