package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * AI扩写角色提示词响应
 */
@Data
public class ExpandPromptResponse {
    /**
     * 扩写后的英文图像提示词
     */
    private String promptText;

    /**
     *  角色描述
     */
    private String description;
}
