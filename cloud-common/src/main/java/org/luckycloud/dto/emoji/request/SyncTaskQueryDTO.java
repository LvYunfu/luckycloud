package org.luckycloud.dto.emoji.request;

import lombok.Data;

/**
 * 同步任务列表查询
 */
@Data
public class SyncTaskQueryDTO {
    /**
     * 用户ID（内部使用，不从前端传入）
     */
    private String userId;

    /**
     * 目标平台
     */
    private String targetPlatform;

    /**
     * 任务状态
     */
    private String taskStatus;
}
