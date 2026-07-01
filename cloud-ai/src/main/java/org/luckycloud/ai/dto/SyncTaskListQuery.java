package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 同步任务列表查询
 */
@Data
public class SyncTaskListQuery {
    /**
     * 目标平台
     */
    private String targetPlatform;

    /**
     * 任务状态
     */
    private String taskStatus;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
    private Integer pageSize = 20;
}
