package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 培训请求DTO
 */
@Data
public class TrainingSessionRequest {

    private String sessionId;

    private String userMessage;


    private Boolean needEvaluation = true;
}
