package org.luckycloud.ai.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 表情包系列响应
 */
@Data
public class EmojiGroupResponse {
    /**
     * 表情包系列ID
     */
    private String emojiGroupId;

    /**
     * IP ID
     */
    private String ipId;

    /**
     * 表情系列描述
     */
    private String description;

    /**
     * 表情包类型
     */
    private String emojiType;

    /**
     * 表情包风格
     */
    private String emojiStyle;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态 1 有效 0无效
     */
    private String status;
}