package org.luckycloud.ai.dto;

import lombok.Data;
import org.luckycloud.ai.domain.EvaluationResult;
import java.util.List;

/**
 * 培训响应DTO
 */
@Data
public class TrainingResponse {
    
    /**
     * 会话ID
     */
    private String sessionId;
    
    /**
     * AI回复内容
     */
    private String aiResponse;
    
    /**
     * 测评结果列表
     */
    private List<EvaluationResult> evaluations;
    
    /**
     * 是否会话结束
     */
    private Boolean sessionEnded;
    
    /**
     * 错误信息
     */
    private String errorMessage;
    
    public static TrainingResponse success(String sessionId, String aiResponse) {
        TrainingResponse response = new TrainingResponse();
        response.setSessionId(sessionId);
        response.setAiResponse(aiResponse);
        response.setSessionEnded(false);
        return response;
    }
    
    public static TrainingResponse successWithEvaluation(String sessionId, String aiResponse, 
                                                        List<EvaluationResult> evaluations) {
        TrainingResponse response = new TrainingResponse();
        response.setSessionId(sessionId);
        response.setAiResponse(aiResponse);
        response.setEvaluations(evaluations);
        response.setSessionEnded(false);
        return response;
    }
    
    public static TrainingResponse error(String errorMessage) {
        TrainingResponse response = new TrainingResponse();
        response.setErrorMessage(errorMessage);
        return response;
    }
}