package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 表情包系列创建命令
 */
@Data
public class EmojiGroupCreateCommand {
    /**
     * 关联的角色 IP ID
     */
    private String ipId;

    /**
     * 系列关键词（逗号分隔，≤100字符）
     */
    private String seriesKeywords;

    /**
     * 生成类型：static(静态) / dynamic(动态)
     */
    private String emojiType;

    /**
     * 表情风格（如"可爱"、"写实"）
     */
    private String emojiStyle;
}