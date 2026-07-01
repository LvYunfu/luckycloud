package org.luckycloud.ai.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 同步任务响应
 */
@Data
public class SyncTaskResponse {
    /**
     * 任务 ID
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
     * 任务状态：pending / processing / success / failed
     */
    private String taskStatus;

    /**
     * 平台返回信息或失败原因
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
