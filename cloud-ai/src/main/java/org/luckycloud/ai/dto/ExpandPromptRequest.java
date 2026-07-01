package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * AI扩写角色提示词请求
 */
@Data
public class ExpandPromptRequest {
    /**
     * 角色关键词（≤50字符）
     */
    private String keyword;
}
