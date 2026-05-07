package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 表情包信息创建命令
 */
@Data
public class EmojiInfoCreateCommand {
    /**
     * 表情包标题
     */
    private String title;

    /**
     * 表情包描述
     */
    private String description;

    /**
     * 表情包系列ID
     */
    private String emojiGroupId;

    /**
     * 表情包路径
     */
    private String emojiUrl;
}
