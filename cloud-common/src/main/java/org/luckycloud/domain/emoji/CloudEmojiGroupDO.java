package org.luckycloud.domain.emoji;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 表情包系列表
 * @TableName cloud_emoji_group
 */
@Data
public class CloudEmojiGroupDO {
    /**
     * 系列唯一标识
     */
    private String emojiGroupId;

    /**
     * 所属用户 ID
     */
    private String userId;

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
     * 表情风格（如“可爱”、“写实”）
     */
    private String emojiStyle;

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
