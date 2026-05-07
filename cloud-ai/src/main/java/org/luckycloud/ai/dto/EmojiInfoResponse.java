package org.luckycloud.ai.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 表情包信息响应
 */
@Data
public class EmojiInfoResponse {
    /**
     * 表情包ID
     */
    private String emojiId;

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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态 1 有效  0无效
     */
    private String status;
}
