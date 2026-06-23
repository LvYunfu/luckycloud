package org.luckycloud.domain.emoji;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 同步任务记录表
 * @TableName cloud_emoji_sync_tasks
 */
@Data
public class CloudEmojiSyncTasksDO {
    /**
     * 任务唯一标识
     */
    private String taskId;

    /**
     * 所属用户 ID
     */
    private String userId;

    /**
     * 关联系列 ID
     */
    private String emojiGroupId;

    /**
     * 同步表情包 ID
     */
    private String syncId;

    /**
     * 目标平台：wechat / douyin
     */
    private String targetPlatform;

    /**
     * 任务状态：pending/processing/success/failed
     */
    private String taskStatus;

    /**
     * 平台返回信息或错误原因（JSON/Text）
     */
    private String platformResponse;

    /**
     * 任务创建时间
     */
    private LocalDateTime createTime;

    /**
     * 任务更新时间
     */
    private LocalDateTime updateTime;
}
