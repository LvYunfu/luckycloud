package org.luckycloud.blog.controller;

import jakarta.annotation.Resource;
import org.luckycloud.blog.dto.request.*;
import org.luckycloud.blog.dto.response.TodoActivityLogResponse;
import org.luckycloud.blog.dto.response.TodoItemResponse;
import org.luckycloud.blog.dto.response.TodoListParticipantResponse;
import org.luckycloud.blog.dto.response.TodoListResponse;
import org.luckycloud.blog.dto.response.UserSearchResponse;
import org.luckycloud.blog.service.TodoListService;
import org.luckycloud.dto.common.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 待办清单控制器
 *
 * @author lvyf
 * @date 2026/4/11
 */
@RestController
@RequestMapping("/todo-list")
public class TodoListController {

    @Resource
    private TodoListService todoListService;

    /**
     * 创建待办清单
     */
    @PostMapping("/create")
    public Response<String> createTodoList(@RequestBody TodoListCreateCommand command) {
        String listId = todoListService.createTodoList(command);
        return Response.successData(listId, "待办清单创建成功");
    }

    /**
     * 更新待办清单
     */
    @PostMapping("/update")
    public Response<Void> updateTodoList(@RequestBody TodoListUpdateCommand command) {
        todoListService.updateTodoList(command);
        return Response.success("待办清单更新成功");
    }

    /**
     * 删除待办清单
     */
    @PostMapping("/delete")
    public Response<Void> deleteTodoList(@RequestParam String listId) {
        todoListService.deleteTodoList(listId);
        return Response.success("待办清单删除成功");
    }

    /**
     * 获取用户的待办清单列表
     */
    @GetMapping("/get-user-todo")
    public List<TodoListResponse> getUserTodoLists() {
        return todoListService.getUserTodoLists();

    }

    /**
     * 获取单个待办清单详情
     */
    @PostMapping("/get-todo")
    public TodoListResponse getTodoTask(@RequestBody TodoItemQuery itemQuery) {
        return todoListService.getTodoTask(itemQuery);
    }

    /**
     * 获取单个待办清单详情
     */
    @PostMapping("/get-todo-items")
    public List<TodoItemResponse> getTodoItems(@RequestBody TodoItemQuery itemQuery) {
        return todoListService.getTodoItems(itemQuery);

    }

    /**
     * 获取待办清单的动态日志
     *
     * @param itemQuery
     * @return
     */
    @PostMapping("/get-activity-log")
    public List<TodoActivityLogResponse> getTodoListActivityLog(@RequestBody TodoItemQuery itemQuery) {
        return todoListService.getTodoListActivityLog(itemQuery);

    }


    /**
     * 创建任务项
     */
    @PostMapping("/create-item")
    public Response<String> createTodoItem(@RequestBody TodoItemCreateCommand command) {
        String itemId = todoListService.createTodoItem(command);
        return Response.successData(itemId, "任务项创建成功");
    }

    /**
     * 更新任务项
     */
    @PostMapping("/update-item")
    public Response<Void> updateTodoItem(@RequestBody TodoItemUpdateCommand command) {
        todoListService.updateTodoItem(command);
        return Response.success("任务项更新成功");
    }

    /**
     * 删除任务项
     */
    @PostMapping("/delete-item")
    public Response<Void> deleteTodoItem(@RequestParam String itemId) {
        todoListService.deleteTodoItem(itemId);
        return Response.success("任务项删除成功");
    }

    /**
     * 完成任务
     */
    @PostMapping("/complete-item")
    public Response<Void> completeTodoItem(@RequestBody TodoItemCompleteCommand command) {
        todoListService.completeTodoItem(command);
        return Response.success("任务完成，恭喜您！");
    }

    /**
     * 重新打开任务（将已完成的任务改为未完成）
     */
    @PostMapping("/reopen-item")
    public Response<Void> reopenTodoItem(@RequestParam String itemId) {
        todoListService.reopenTodoItem(itemId);
        return Response.success("任务已重新打开");
    }

    /**
     * 从未完成的任务中随机选择一个
     */
    @PostMapping("/random-uncompleted")
    public TodoItemResponse getRandomUncompletedItem(@RequestBody TodoItemQuery query) {
        return todoListService.getRandomUncompletedItem(query);

    }

    /**
     * 邀请用户参与清单
     */
    @PostMapping("/invite-user")
    public Response<Void> inviteUser(@RequestBody TodoListInviteCommand command) {
        todoListService.inviteUser(command);
        return Response.success("邀请成功");
    }

    /**
     * 获取清单的参与者列表
     */
    @GetMapping("/get-participants")
    public List<TodoListParticipantResponse> getListParticipants(@RequestParam String listId) {
        return todoListService.getListParticipants(listId);

    }

    /**
     * 退出清单(参与者主动退出)
     */
    @PostMapping("/quit-list")
    public Response<Void> quitList(@RequestParam String listId) {
        todoListService.quitList(listId);
        return Response.success("已退出清单");
    }

    /**
     * 移除参与者(清单创建者剔除参与者)
     */
    @PostMapping("/remove-participant")
    public Response<Void> removeParticipant(@RequestParam String listId, @RequestParam String userId) {
        todoListService.removeParticipant(listId, userId);
        return Response.success("已移除参与者");
    }

    /**
     * 获取用户参与的清单列表
     */
    @GetMapping("/get-joined-todo")
    public List<TodoListResponse> getJoinedTodoLists() {
        return todoListService.getJoinedTodoLists();

    }

    /**
     * 搜索用户(通过用户名或邮箱)
     */
    @PostMapping("/search-users")
    public List<UserSearchResponse> searchUsers(@RequestBody UserSearchCommand command) {
        return todoListService.searchUsers(command);

    }
}
