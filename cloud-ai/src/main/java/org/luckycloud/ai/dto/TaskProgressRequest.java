package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 查询任务进度请求
 */
@Data
public class TaskProgressRequest {
    /**
     * 任务 ID
     */
    private String taskId;
}
