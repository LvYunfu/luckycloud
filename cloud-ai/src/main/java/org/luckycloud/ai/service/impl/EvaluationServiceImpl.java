package org.luckycloud.ai.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.luckycloud.ai.domain.CustomerProfile;
import org.luckycloud.ai.domain.EvaluationResult;
import org.luckycloud.ai.domain.TrainingSession;
import org.luckycloud.ai.service.CustomerProfileService;
import org.luckycloud.ai.service.EvaluationService;
import org.luckycloud.ai.service.PromptService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * AI回复测评服务实现类
 */
@Slf4j
@Service
public class EvaluationServiceImpl implements EvaluationService {
    
    @Resource
    private ChatClient chatClient;
    
    @Resource
    private PromptService promptService;
    
    @Resource
    private CustomerProfileService customerProfileService;
    
    @Override
    public List<EvaluationResult> evaluateResponse(TrainingSession session, String aiResponse) {
        List<EvaluationResult> results = new ArrayList<>();
        
        try {
            // 获取客户画像
            CustomerProfile profile = customerProfileService.getProfileById(session.getProfileId());
            if (profile == null) {
                log.warn("无法获取客户画像，跳过测评");
                return results;
            }
            
            // 多维度测评
            results.add(evaluateResponseQuality(session, aiResponse, profile));
            results.add(evaluateProblemSolving(session, aiResponse, profile));
            results.add(evaluateCommunicationSkill(session, aiResponse, profile));
            results.add(evaluatePatienceLevel(session, aiResponse, profile));
            
            // 计算总体评分
            EvaluationResult overall = evaluateOverallPerformance(session, aiResponse, results);
            results.add(overall);
            
        } catch (Exception e) {
            log.error("测评过程中发生错误", e);
            // 返回基础测评结果
            results.add(createBasicEvaluation(session.getSessionId(), aiResponse));
        }
        
        return results;
    }
    
    @Override
    public Integer evaluateByCustomerProfile(TrainingSession session, String aiResponse) {
        CustomerProfile profile = customerProfileService.getProfileById(session.getProfileId());
        if (profile == null) {
            return 50; // 默认分数
        }
        
        try {
            String prompt = promptService.buildEvaluationPrompt("overallPerformance", profile, aiResponse);
            
            String response = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
            
            return parseScore(response);
        } catch (Exception e) {
            log.error("通过客户画像评估失败", e);
            return 50;
        }
    }
    
    @Override
    public String generateDetailedFeedback(TrainingSession session, String aiResponse) {
        CustomerProfile profile = customerProfileService.getProfileById(session.getProfileId());
        if (profile == null) {
            return "无法生成详细反馈：客户画像缺失";
        }
        
        try {
            String prompt = promptService.buildEvaluationPrompt("detailedFeedback", profile, aiResponse);
            
            return chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
        } catch (Exception e) {
            log.error("生成详细反馈失败", e);
            return "生成反馈时出现错误";
        }
    }
    
    /**
     * 评估回复质量
     */
    private EvaluationResult evaluateResponseQuality(TrainingSession session, String aiResponse, CustomerProfile profile) {
        try {
            String prompt = promptService.buildEvaluationPrompt("responseQuality", profile, aiResponse);
            
            String response = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
            
            return parseEvaluationResult(session.getSessionId(), aiResponse, response, 
                                       EvaluationResult.EvaluationDimension.RESPONSE_QUALITY);
        } catch (Exception e) {
            log.error("回复质量评估失败", e);
            return createDefaultEvaluation(session.getSessionId(), aiResponse, 
                                         EvaluationResult.EvaluationDimension.RESPONSE_QUALITY, 60);
        }
    }
    
    /**
     * 评估问题解决能力
     */
    private EvaluationResult evaluateProblemSolving(TrainingSession session, String aiResponse, CustomerProfile profile) {
        try {
            String prompt = promptService.buildEvaluationPrompt("problemSolving", profile, aiResponse);
            
            String response = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
            
            return parseEvaluationResult(session.getSessionId(), aiResponse, response, 
                                       EvaluationResult.EvaluationDimension.PROBLEM_SOLVING);
        } catch (Exception e) {
            log.error("问题解决能力评估失败", e);
            return createDefaultEvaluation(session.getSessionId(), aiResponse, 
                                         EvaluationResult.EvaluationDimension.PROBLEM_SOLVING, 55);
        }
    }
    
    /**
     * 评估沟通技巧
     */
    private EvaluationResult evaluateCommunicationSkill(TrainingSession session, String aiResponse, CustomerProfile profile) {
        try {
            String prompt = promptService.buildEvaluationPrompt("communicationSkill", profile, aiResponse);
            
            String response = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
            
            return parseEvaluationResult(session.getSessionId(), aiResponse, response, 
                                       EvaluationResult.EvaluationDimension.COMMUNICATION_SKILL);
        } catch (Exception e) {
            log.error("沟通技巧评估失败", e);
            return createDefaultEvaluation(session.getSessionId(), aiResponse, 
                                         EvaluationResult.EvaluationDimension.COMMUNICATION_SKILL, 65);
        }
    }
    
    /**
     * 评估耐心程度
     */
    private EvaluationResult evaluatePatienceLevel(TrainingSession session, String aiResponse, CustomerProfile profile) {
        try {
            String prompt = promptService.buildEvaluationPrompt("patienceLevel", profile, aiResponse);
            
            String response = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
            
            return parseEvaluationResult(session.getSessionId(), aiResponse, response, 
                                       EvaluationResult.EvaluationDimension.PATIENCE_LEVEL);
        } catch (Exception e) {
            log.error("耐心程度评估失败", e);
            return createDefaultEvaluation(session.getSessionId(), aiResponse, 
                                         EvaluationResult.EvaluationDimension.PATIENCE_LEVEL, 50);
        }
    }
    
    /**
     * 评估整体表现
     */
    private EvaluationResult evaluateOverallPerformance(TrainingSession session, String aiResponse, 
                                                      List<EvaluationResult> dimensionResults) {
        // 计算平均分
        int totalScore = dimensionResults.stream()
                .mapToInt(EvaluationResult::getScore)
                .sum();
        int averageScore = totalScore / Math.max(1, dimensionResults.size());
        
        String feedback = String.format("综合评估结果：各项指标平均得分为%d分。%s",
                averageScore,
                generateOverallFeedback(dimensionResults));
        
        return new EvaluationResult(
                session.getSessionId(),
                aiResponse,
                averageScore,
                EvaluationResult.EvaluationDimension.OVERALL_PERFORMANCE,
                feedback
        );
    }
    
    /**
     * 解析评估结果
     */
    private EvaluationResult parseEvaluationResult(String sessionId, String aiResponse, String modelResponse,
                                                 EvaluationResult.EvaluationDimension dimension) {
        try {
            String[] parts = modelResponse.split("\\|", 2);
            if (parts.length == 2) {
                int score = parseScore(parts[0].trim());
                String feedback = parts[1].trim();
                
                return new EvaluationResult(sessionId, aiResponse, score, dimension, feedback);
            }
        } catch (Exception e) {
            log.warn("解析评估结果失败，使用默认值", e);
        }
        
        return createDefaultEvaluation(sessionId, aiResponse, dimension, 60);
    }
    
    /**
     * 创建默认评估结果
     */
    private EvaluationResult createDefaultEvaluation(String sessionId, String aiResponse,
                                                   EvaluationResult.EvaluationDimension dimension, int defaultScore) {
        return new EvaluationResult(
                sessionId,
                aiResponse,
                defaultScore,
                dimension,
                "系统自动评估，默认分数"
        );
    }
    
    /**
     * 创建基础评估结果
     */
    private EvaluationResult createBasicEvaluation(String sessionId, String aiResponse) {
        return new EvaluationResult(
                sessionId,
                aiResponse,
                50,
                EvaluationResult.EvaluationDimension.OVERALL_PERFORMANCE,
                "基础评估结果"
        );
    }
    
    /**
     * 解析分数
     */
    private int parseScore(String scoreStr) {
        try {
            // 提取数字
            String cleanScore = scoreStr.replaceAll("[^0-9]", "");
            int score = Integer.parseInt(cleanScore);
            return Math.max(0, Math.min(100, score)); // 限制在0-100范围内
        } catch (Exception e) {
            return 50; // 默认分数
        }
    }
    
    /**
     * 生成整体反馈
     */
    private String generateOverallFeedback(List<EvaluationResult> results) {
        StringBuilder feedback = new StringBuilder();
        
        // 找出最高分和最低分的维度
        EvaluationResult highest = results.stream()
                .max((r1, r2) -> r1.getScore().compareTo(r2.getScore()))
                .orElse(null);
        
        EvaluationResult lowest = results.stream()
                .min((r1, r2) -> r1.getScore().compareTo(r2.getScore()))
                .orElse(null);
        
        if (highest != null && lowest != null) {
            feedback.append("优势领域：").append(highest.getDimension().getDescription())
                    .append("(得分").append(highest.getScore()).append("分)；");
            feedback.append("待改进领域：").append(lowest.getDimension().getDescription())
                    .append("(得分").append(lowest.getScore()).append("分)");
        }
        
        return feedback.toString();
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