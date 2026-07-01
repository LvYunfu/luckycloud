package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 重新生成单个表情包请求
 */
@Data
public class RegenerateRequest {
    /**
     * 表情包 ID
     */
    private String emojiId;
}
