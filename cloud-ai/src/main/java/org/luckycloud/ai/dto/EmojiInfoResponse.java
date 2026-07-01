package org.luckycloud.ai.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 表情包信息响应
 */
@Data
public class EmojiInfoResponse {
    /**
     * 表情包唯一标识
     */
    private String emojiId;

    /**
     * 关联的角色 IP ID
     */
    private String ipId;

    /**
     * 所属系列 ID
     */
    private String emojiGroupId;

    /**
     * 表情名称
     */
    private String name;

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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
