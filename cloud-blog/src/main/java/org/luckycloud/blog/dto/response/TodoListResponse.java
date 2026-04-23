package org.luckycloud.blog.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 待办清单响应
 * @author lvyf
 * @date 2026/4/11
 */
@Data
public class TodoListResponse {

    /**
     * 清单ID
     */
    private String listId;

    private String userId;

    private String userName;

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

    private int total;

    private int completed;


    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
