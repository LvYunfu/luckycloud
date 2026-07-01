package org.luckycloud.ai.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色IP响应
 */
@Data
public class EmojiIpResponse {
    /**
     * 角色 IP 唯一标识
     */
    private String ipId;

    /**
     * 所属用户 ID
     */
    private String userId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色图片 OSS/CDN URL
     */
    private String imageUrl;

    /**
     * 来源类型：ai_generated / user_upload
     */
    private String sourceType;

    /**
     * 用户输入的角色关键词（AI生成时记录）
     */
    private String sourceKeyword;

    /**
     * AI扩写后的最终图像提示词
     */
    private String promptText;

    /**
     * 角色描述（用户补充）
     */
    private String description;

    /**
     * 是否默认角色：0-否，1-是
     */
    private Integer isDefault;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态：1-有效，0-无效
     */
    private String status;
}
