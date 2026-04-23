package org.luckycloud.blog.dto.request;

import lombok.Data;

/**
 * 邀请用户参与清单请求
 * @author lvyf
 * @date 2026/4/22
 */
@Data
public class TodoListInviteCommand {

    /**
     * 清单ID
     */
    private String listId;

    /**
     * 被邀请用户ID
     */
    private String inviteUserId;
}
