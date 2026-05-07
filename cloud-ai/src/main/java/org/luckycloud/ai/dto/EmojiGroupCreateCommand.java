package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 表情包系列创建命令
 */
@Data
public class EmojiGroupCreateCommand {
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
}