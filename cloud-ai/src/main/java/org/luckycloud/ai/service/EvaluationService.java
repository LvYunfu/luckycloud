package org.luckycloud.ai.service;

import org.luckycloud.ai.domain.EvaluationResult;
import org.luckycloud.ai.domain.TrainingSession;
import java.util.List;

/**
 * AI回复测评服务接口
 */
public interface EvaluationService {
    
    /**
     * 对AI回复进行测评
     */
    List<EvaluationResult> evaluateResponse(TrainingSession session, String aiResponse);
    
    /**
     * 根据客户画像评估回复质量
     */
    Integer evaluateByCustomerProfile(TrainingSession session, String aiResponse);
    
    /**
     * 生成详细的测评反馈
     */
    String generateDetailedFeedback(TrainingSession session, String aiResponse);
}