package org.luckycloud.domain.todo;

import lombok.Data;

/**
 * 任务统计结果
 * @author lvyf
 * @date 2026/4/12
 */
@Data
public class TodoItemStatistics {

    private String listId;

    /**
     * 任务总数
     */
    private int totalCount;

    /**
     * 已完成任务数
     */
    private int completedCount;

    /**
     * 待完成任务数
     */
    private int todoCount;
}
