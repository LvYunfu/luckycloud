package org.luckycloud.blog.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 任务项响应
 * @author lvyf
 * @date 2026/4/11
 */
@Data
public class TodoItemResponse {

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
