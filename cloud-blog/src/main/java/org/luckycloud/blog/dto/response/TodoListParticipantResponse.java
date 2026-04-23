package org.luckycloud.blog.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 清单参与者响应
 * @author lvyf
 * @date 2026/4/22
 */
@Data
public class TodoListParticipantResponse {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 清单ID
     */
    private String listId;

    /**
     * 加入时间
     */
    private LocalDateTime createTime;

    /**
     * 状态
     */
    private String status;
}
