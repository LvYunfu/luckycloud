package org.luckycloud.dto.emoji.request;

import lombok.Data;

/**
 * 表情包系列列表查询
 */
@Data
public class EmojiGroupQueryDTO {
    /**
     * 用户ID（内部使用，不从前端传入）
     */
    private String userId;

    /**
     * 系列关键词（模糊搜索）
     */
    private String seriesKeywords;

    /**
     * 生成类型筛选
     */
    private String emojiType;

    /**
     * 角色IP筛选
     */
    private String ipId;
}
