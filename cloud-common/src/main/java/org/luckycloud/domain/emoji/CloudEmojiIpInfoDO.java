package org.luckycloud.domain.emoji;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 表情主题ip
 * @TableName cloud_emoji_ip_info
 */
@Data
public class CloudEmojiIpInfoDO {
    /**
     * 主键
     */
    private String ipId;

    /**
     * ip标题
     */
    private String title;

    /**
     * IP描述
     */
    private String description;

    /**
     * ip文件路径
     */
    private String ipUrl;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态 1 有效 0 无效
     */
    private String status;
}
