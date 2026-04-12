package org.luckycloud.domain.todo;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 任务项表
 * @TableName cloud_todo_items
 */
@Data
public class CloudTodoItemsDO {
    /**
     * 任务ID
     */
    private String itemId;

    /**
     * 所属清单ID
     */
    private String listId;

    /**
     * 任务标题
     */
    private String title;

    /**
     * 任务详细描述
     */
    private String description;

    /**
     * 状态：IS00-待办, IS01-已完成
     */
    private String itemStatus;

    /**
     * 排序权重
     */
    private Integer sortOrder;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 完成时间
     */
    private LocalDateTime completedTime;
}
