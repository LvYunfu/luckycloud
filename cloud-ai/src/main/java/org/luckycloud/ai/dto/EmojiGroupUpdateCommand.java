package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 表情包系列更新命令
 */
@Data
public class EmojiGroupUpdateCommand {
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
     * 状态 1 有效 0无效
     */
    private String status;
}