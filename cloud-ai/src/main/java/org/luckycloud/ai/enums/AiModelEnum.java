package org.luckycloud.ai.enums;

import lombok.Getter;

/**
 * AI模型枚举
 */
@Getter
public enum AiModelEnum {
    
    GPT_3_5_TURBO("gpt-3.5-turbo", "GPT-3.5 Turbo", "适合日常对话"),
    GPT_4("gpt-4", "GPT-4", "更智能的对话能力"),
    GPT_4_TURBO("gpt-4-turbo", "GPT-4 Turbo", "最新版本，性能更强"),
    DOUBAO_SEED("doubao-seed-1-8-251228", "豆包Seed", "字节跳动大模型"),
    CLAUDE_3("claude-3-opus", "Claude 3 Opus", "Anthropic的优秀模型");
    
    private final String modelId;
    private final String modelName;
    private final String description;
    
    AiModelEnum(String modelId, String modelName, String description) {
        this.modelId = modelId;
        this.modelName = modelName;
        this.description = description;
    }
    
    public static AiModelEnum getByModelId(String modelId) {
        for (AiModelEnum model : values()) {
            if (model.getModelId().equals(modelId)) {
                return model;
            }
        }
        throw new IllegalArgumentException("不支持的模型ID: " + modelId);
    }
}