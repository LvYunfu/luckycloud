package org.luckycloud.dto.emoji.request;

import lombok.Data;

/**
 * 表情包资产列表查询
 */
@Data
public class EmojiInfoQueryDTO {
    /**
     * 用户ID（内部使用，不从前端传入）
     */
    private String userId;

    /**
     * 表情名称（模糊搜索）
     */
    private String name;

    /**
     * 类型筛选
     */
    private String type;

    /**
     * 系列ID筛选
     */
    private String emojiGroupId;

    /**
     * 角色IP筛选
     */
    private String ipId;

    /**
     * 提示词模糊搜索
     */
    private String promptText;
}
