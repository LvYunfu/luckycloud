package org.luckycloud.domain.todo;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 待办清单表
 * @TableName cloud_todo_lists
 */
@Data
public class CloudTodoListsDO {
    /**
     * 清单ID
     */
    private String listId;

    /**
     * 所属用户ID
     */
    private String userId;

    /**
     * 清单标题
     */
    private String title;

    /**
     * 清单描述
     */
    private String description;

    /**
     * 是否启用：1-是，0-否
     */
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
