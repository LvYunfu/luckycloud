package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 表情包系列列表查询
 */
@Data
public class EmojiGroupListQuery {
    /**
     * 按系列关键词搜索
     */
    private String seriesKeywords;

    /**
     * 按类型筛选
     */
    private String emojiType;

    /**
     * 按角色筛选
     */
    private String ipId;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
    private Integer pageSize = 20;
}
