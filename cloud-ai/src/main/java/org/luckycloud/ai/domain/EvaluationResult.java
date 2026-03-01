package org.luckycloud.ai.domain;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 测评结果实体类
 */
@Data
public class EvaluationResult {
    
    /**
     * 测评ID
     */
    private String evaluationId;
    
    /**
     * 会话ID
     */
    private String sessionId;
    
    /**
     * AI回复内容
     */
    private String aiResponse;
    
    /**
     * 测评分数（1-100）
     */
    private Integer score;
    
    /**
     * 测评维度
     */
    private EvaluationDimension dimension;
    
    /**
     * 测评反馈
     */
    private String feedback;
    
    /**
     * 测评时间
     */
    private LocalDateTime evaluationTime;
    
    /**
     * 测评维度枚举
     */
    public enum EvaluationDimension {
        RESPONSE_QUALITY("回复质量"),
        PROBLEM_SOLVING("问题解决能力"),
        COMMUNICATION_SKILL("沟通技巧"),
        PATIENCE_LEVEL("耐心程度"),
        OVERALL_PERFORMANCE("整体表现");
        
        private final String description;
        
        EvaluationDimension(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    public EvaluationResult() {
        this.evaluationTime = LocalDateTime.now();
    }
    
    public EvaluationResult(String sessionId, String aiResponse, Integer score, 
                          EvaluationDimension dimension, String feedback) {
        this();
        this.sessionId = sessionId;
        this.aiResponse = aiResponse;
        this.score = score;
        this.dimension = dimension;
        this.feedback = feedback;
    }
}