package org.luckycloud.ai.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 表情包系列响应
 */
@Data
public class EmojiGroupResponse {
    /**
     * 系列唯一标识
     */
    private String emojiGroupId;

    /**
     * 关联的角色 IP ID
     */
    private String ipId;

    /**
     * 用户输入的系列关键词（逗号分隔）
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

    /**
     * 系列下表情包数量
     */
    private Integer emojiCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}