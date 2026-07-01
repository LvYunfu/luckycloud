package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 角色IP列表查询
 */
@Data
public class EmojiIpListQuery {
    /**
     * 用户ID（内部使用，不从前端传入）
     */
    private String userId;

    /**
     * 按角色名称模糊搜索
     */
    private String name;

    /**
     * 按来源类型筛选
     */
    private String sourceType;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
    private Integer pageSize = 20;
}
