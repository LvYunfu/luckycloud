package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 表情包信息创建命令
 */
@Data
public class EmojiInfoCreateCommand {
    /**
     * 表情名称
     */
    private String name;

    /**
     * 情境文字
     */
    private String context;

    /**
     * 动作细节
     */
    private String actionDetail;

    /**
     * 表情元素
     */
    private String expressionElements;

    /**
     * AI扩写后的最终图像提示词
     */
    private String promptText;

    /**
     * 类型：static / dynamic
     */
    private String type;

    /**
     * 文件 URL（PNG 或 GIF）
     */
    private String fileUrl;

    /**
     * 文件大小（KB）
     */
    private Integer fileSize;

    /**
     * 所属用户 ID
     */
    private String userId;

    /**
     * 关联的角色 IP ID
     */
    private String ipId;

    /**
     * 所属系列 ID
     */
    private String emojiGroupId;
}
