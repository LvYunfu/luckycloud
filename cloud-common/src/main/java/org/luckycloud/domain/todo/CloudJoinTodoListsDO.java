package org.luckycloud.domain.todo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 清单参与表
 * @TableName cloud_join_todo_lists
 */
@Data
public class CloudJoinTodoListsDO {
    /**
     * 主键
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 清单ID
     */
    private String listId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     *
     */
    private String status;
}
