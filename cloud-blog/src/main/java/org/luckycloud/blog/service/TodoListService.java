package org.luckycloud.blog.service;

import org.luckycloud.blog.dto.request.*;
import org.luckycloud.blog.dto.response.*;

import java.util.List;

/**
 * 待办清单服务接口
 * @author lvyf
 * @date 2026/4/11
 */
public interface TodoListService {

    /**
     * 创建待办清单
     */
    String createTodoList(TodoListCreateCommand command);

    /**
     * 更新待办清单
     */
    void updateTodoList(TodoListUpdateCommand command);

    /**
     * 删除待办清单
     */
    void deleteTodoList(String listId);

    /**
     * 获取用户的待办清单列表
     */
    List<TodoListResponse> getUserTodoLists();

    /**
     * 获取单个待办清单详情
     */
    TodoListResponse getTodoTask(TodoItemQuery query);

    /**
     * 创建任务项
     */
    String createTodoItem(TodoItemCreateCommand command);

    /**
     * 更新任务项
     */
    void updateTodoItem(TodoItemUpdateCommand command);

    /**
     * 删除任务项
     */
    void deleteTodoItem(String itemId);

    /**
     * 完成任务
     */
    void completeTodoItem(TodoItemCompleteCommand command);

    /**
     * 重新打开任务（将已完成的任务改为未完成）
     */
    void reopenTodoItem(String itemId);

    /**
     * 从未完成的任务中随机选择一个
     */
    TodoItemResponse getRandomUncompletedItem(TodoItemQuery query);

    List<TodoActivityLogResponse> getTodoListActivityLog(TodoItemQuery itemQuery);

    List<TodoItemResponse> getTodoItems(TodoItemQuery itemQuery);

    /**
     * 邀请用户参与清单
     */
    void inviteUser(TodoListInviteCommand command);

    /**
     * 获取清单的参与者列表
     */
    List<TodoListParticipantResponse> getListParticipants(String listId);

    /**
     * 退出清单
     */
    void quitList(String listId);

    /**
     * 移除参与者(清单创建者使用)
     */
    void removeParticipant(String listId, String userId);

    /**
     * 获取用户参与的清单列表
     */
    List<TodoListResponse> getJoinedTodoLists();

    /**
     * 搜索用户(通过用户名或邮箱)
     */
    List<UserSearchResponse> searchUsers(UserSearchCommand command);
}
