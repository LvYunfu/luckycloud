package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 表情包资产列表查询
 */
@Data
public class EmojiInfoListQuery {
    /**
     * 按表情名称搜索
     */
    private String name;

    /**
     * 按类型筛选：static / dynamic
     */
    private String type;

    /**
     * 按所属系列筛选
     */
    private String emojiGroupId;

    /**
     * 按角色IP筛选
     */
    private String ipId;

    /**
     * 按提示词内容模糊搜索
     */
    private String promptText;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
    private Integer pageSize = 20;
}
