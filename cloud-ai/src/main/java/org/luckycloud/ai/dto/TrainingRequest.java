package org.luckycloud.ai.dto;

import lombok.Data;
import org.luckycloud.ai.domain.CustomerProfile;
import org.luckycloud.ai.enums.AiModelEnum;

/**
 * 培训请求DTO
 */
@Data
public class TrainingRequest {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 客户画像
     */
    private String  customerId;

    /**
     * AI模型类型
     */
    private String model;

    /**
     * 用户输入的消息
     */
    private String userMessage;

    /**
     * 是否需要测评
     */
    private Boolean needEvaluation = true;
}
