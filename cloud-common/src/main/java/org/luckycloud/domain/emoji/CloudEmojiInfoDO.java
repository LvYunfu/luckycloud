package org.luckycloud.domain.emoji;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 表情包资产表
 * @TableName cloud_emoji_info
 */
@Data
public class CloudEmojiInfoDO {
    /**
     * 表情包唯一标识
     */
    private String emojiId;

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

    /**
     * 状态：1-有效，0-无效
     */
    private String status;

    /**
     * 生成状态：processing-生成中，success-生成成功，failed-生成失败
     */
    private String generateStatus;
}
