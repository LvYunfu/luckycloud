package org.luckycloud.blog.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.luckycloud.blog.convert.TodoConvert;
import org.luckycloud.blog.dto.request.*;
import org.luckycloud.blog.dto.response.TodoActivityLogResponse;
import org.luckycloud.blog.dto.response.TodoItemResponse;
import org.luckycloud.blog.dto.response.TodoListResponse;
import org.luckycloud.blog.service.TodoListService;
import org.luckycloud.domain.todo.CloudTodoItemsActivityLogsDO;
import org.luckycloud.domain.todo.CloudTodoItemsDO;
import org.luckycloud.domain.todo.CloudTodoListsDO;
import org.luckycloud.domain.todo.TodoItemStatistics;
import org.luckycloud.exception.BusinessException;
import org.luckycloud.mapper.todo.CloudTodoItemsActivityLogsMapper;
import org.luckycloud.mapper.todo.CloudTodoItemsMapper;
import org.luckycloud.mapper.todo.CloudTodoListsMapper;
import org.luckycloud.security.util.UserUtils;
import org.luckycloud.utils.GenerateIdUtils;
import org.luckycloud.utils.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.luckycloud.constant.SystemConstant.DISABLE;
import static org.luckycloud.dto.common.ResponseCode.OPERATE_FAILED;

/**
 * 待办清单服务实现
 *
 * @author lvyf
 * @date 2026/4/11
 */
@Log4j2
@Service
public class TodoListServiceImpl implements TodoListService {

    @Resource
    private CloudTodoListsMapper todoListsMapper;

    @Resource
    private CloudTodoItemsMapper todoItemsMapper;

    @Resource
    private CloudTodoItemsActivityLogsMapper activityLogsMapper;

    @Resource
    private TodoConvert todoConvert;

    private static final String ITEM_STATUS_UNCOMPLETED = "IS00";
    private static final String ITEM_STATUS_COMPLETED = "IS01";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createTodoList(TodoListCreateCommand command) {
        CloudTodoListsDO todoList = todoConvert.convert2DO(command);
        String userId = UserUtils.getUserId();
        todoList.setListId(GenerateIdUtils.generateId());
        todoListsMapper.insert(todoList);
        log.info("创建待办清单成功，listId: {}, userId: {}", todoList.getListId(), userId);
        return todoList.getListId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTodoList(TodoListUpdateCommand command) {
        checkListId(command.getListId());
        CloudTodoListsDO updateDO = todoConvert.convert2DO(command);
        todoListsMapper.updateByPrimaryKeySelective(updateDO);
        log.info("更新待办清单成功，listId: {}", command.getListId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTodoList(String listId) {
        checkListId(listId);
        // 逻辑删除，将状态设置为禁用
        CloudTodoListsDO updateDO = new CloudTodoListsDO();
        updateDO.setListId(listId);
        updateDO.setStatus(DISABLE);
        updateDO.setUpdateTime(LocalDateTime.now());
        todoListsMapper.updateByPrimaryKeySelective(updateDO);
        log.info("删除待办清单成功，listId: {}", listId);
    }

    @Override
    public List<TodoListResponse> getUserTodoLists() {
        String userId = UserUtils.getUserId();
        List<CloudTodoListsDO> lists = todoListsMapper.selectByUserId(userId);
        if (CollectionUtils.isEmpty(lists)) {
            return Collections.emptyList();
        }
        List<TodoItemStatistics> statisticsList = todoItemsMapper.countTodoItems(lists.stream().map(CloudTodoListsDO::getListId).toList());
        Map<String, TodoItemStatistics> statisticsMap = statisticsList.stream()
                .collect(Collectors.toMap(TodoItemStatistics::getListId, e -> e));
        return lists.stream()
                .map(e -> {
                    TodoListResponse response = todoConvert.convert2TodoListResponse(e);
                    TodoItemStatistics statistics = statisticsMap.getOrDefault(e.getListId(), new TodoItemStatistics());
                    response.setTotal(statistics.getTotalCount());
                    response.setCompleted(statistics.getCompletedCount());
                    return response;
                }).toList();
    }

    @Override
    public TodoListResponse getTodoTask(TodoItemQuery query) {
        CloudTodoListsDO todoList = checkListId(query.getListId());
        TodoListResponse response = todoConvert.convert2TodoListResponse(todoList);
        List<TodoItemStatistics> statisticsList = todoItemsMapper.countTodoItems(Collections.singletonList(response.getListId()));
        if (CollectionUtils.isEmpty(statisticsList)) {
            return response;
        }
        TodoItemStatistics statistics = statisticsList.get(0);
        if (!Objects.isNull(statistics)) {
            response.setTotal(statistics.getTotalCount());
            response.setCompleted(statistics.getCompletedCount());
        }
        return response;
    }

    @Override
    public List<TodoItemResponse> getTodoItems(TodoItemQuery query) {
        // 查询任务项列表
        List<CloudTodoItemsDO> items = todoItemsMapper.selectByList(todoConvert.convert2Query(query));
        if (!CollectionUtils.isEmpty(items)) {
            return items.stream()
                    .map(e -> todoConvert.convert2TodoItemResponse(e))
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createTodoItem(TodoItemCreateCommand command) {
        // 验证清单是否存在并检查权限
        checkListId(command.getListId());

        CloudTodoItemsDO item = todoConvert.convert2DO(command);
        item.setItemId(GenerateIdUtils.generateId());
        item.setSortOrder(command.getSortOrder() != null ? command.getSortOrder() : 0);

        todoItemsMapper.insert(item);
        log.info("创建任务项成功，itemId: {}, listId: {}", item.getItemId(), command.getListId());

        return item.getItemId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTodoItem(TodoItemUpdateCommand command) {
        // 验证任务项存在并检查权限
        checkItemId(command.getItemId());

        CloudTodoItemsDO updateDO = todoConvert.convert2DO(command);
        updateDO.setItemId(command.getItemId());

        todoItemsMapper.updateByPrimaryKeySelective(updateDO);
        log.info("更新任务项成功，itemId: {}", command.getItemId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTodoItem(String itemId) {
        // 验证任务项存在并检查权限
        checkItemId(itemId);
        CloudTodoItemsDO updateDO = new CloudTodoItemsDO();
        updateDO.setItemId(itemId);
        updateDO.setStatus(DISABLE);
        updateDO.setUpdateTime(LocalDateTime.now());
        todoItemsMapper.updateByPrimaryKeySelective(updateDO);
        log.info("删除任务项成功，itemId: {}", itemId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeTodoItem(TodoItemCompleteCommand command) {
        // 验证任务项存在并检查权限
        checkItemId(command.getItemId());
        // 更新任务状态为已完成
        CloudTodoItemsDO updateDO = new CloudTodoItemsDO();
        updateDO.setItemId(command.getItemId());
        updateDO.setItemStatus(ITEM_STATUS_COMPLETED);
        updateDO.setCompletedTime(LocalDateTime.now());
        updateDO.setUpdateTime(LocalDateTime.now());
        todoItemsMapper.updateByPrimaryKeySelective(updateDO);

        // 创建活动记录
        if (!ObjectUtils.isEmpty(command.getContentText()) ||
                !ObjectUtils.isEmpty(command.getImageUrls()) ||
                !ObjectUtils.isEmpty(command.getLocation())) {

            CloudTodoItemsActivityLogsDO logDO = new CloudTodoItemsActivityLogsDO();
            logDO.setLogId(GenerateIdUtils.generateId());
            logDO.setItemId(command.getItemId());
            logDO.setContentText(command.getContentText());
            logDO.setImageUrls(JsonUtils.toJsonString(command.getImageUrls()));
            logDO.setLocation(command.getLocation());
            logDO.setCreateTime(LocalDateTime.now());
            logDO.setUpdateTime(LocalDateTime.now());
            activityLogsMapper.insert(logDO);
        }

        log.info("完成任务成功，itemId: {}", command.getItemId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reopenTodoItem(String itemId) {
        // 验证任务项存在并检查权限
        CloudTodoItemsDO existItem = checkItemId(itemId);

        if (ITEM_STATUS_UNCOMPLETED.equals(existItem.getItemStatus())) {
            throw new BusinessException(OPERATE_FAILED, "任务未完成，无需重新打开");
        }

        // 更新任务状态为未完成
        CloudTodoItemsDO updateDO = new CloudTodoItemsDO();
        updateDO.setItemId(itemId);
        updateDO.setItemStatus(ITEM_STATUS_UNCOMPLETED);
        updateDO.setCompletedTime(null);
        updateDO.setUpdateTime(LocalDateTime.now());
        todoItemsMapper.updateByPrimaryKeySelective(updateDO);

        log.info("重新打开任务成功，itemId: {}", itemId);
    }

    @Override
    public TodoItemResponse getRandomUncompletedItem(TodoItemQuery query) {
        // 验证清单存在并检查权限
        checkListId(query.getListId());

        // 查询未完成的任务列表
        List<CloudTodoItemsDO> uncompletedItems = todoItemsMapper.selectUncompletedByListId(query.getListId());

        if (CollectionUtils.isEmpty(uncompletedItems)) {
            throw new BusinessException(OPERATE_FAILED, "没有未完成的任务");
        }

        // 如果只有一个任务，直接返回
        if (uncompletedItems.size() == 1) {
            return todoConvert.convert2TodoItemResponse(uncompletedItems.get(0));
        }

        // 随机选择一个，最多重试3次避免选中相同的itemId
        Random random = new Random();
        CloudTodoItemsDO selectedItem = null;
        int maxRetries = 3;

        for (int i = 0; i < maxRetries; i++) {
            CloudTodoItemsDO candidate = uncompletedItems.get(random.nextInt(uncompletedItems.size()));
            // 如果随机到的itemId与查询的不同，或者已经是最后一次尝试，则选中
            if (!candidate.getItemId().equals(query.getItemId()) || i == maxRetries - 1) {
                selectedItem = candidate;
                break;
            }
        }

        return todoConvert.convert2TodoItemResponse(selectedItem);
    }


    @Override
    public List<TodoActivityLogResponse> getTodoListActivityLog(TodoItemQuery itemQuery) {
        List<CloudTodoItemsActivityLogsDO> list = activityLogsMapper.selectByItemId(itemQuery.getItemId());
        return todoConvert.convert2ActivityLogResponse(list);
    }

    /**
     * 检查清单ID是否存在并验证权限
     */
    private CloudTodoListsDO checkListId(String listId) {
        CloudTodoListsDO todoList = todoListsMapper.selectByListId(listId);
        if (ObjectUtils.isEmpty(todoList)) {
            throw new BusinessException(OPERATE_FAILED, "待办清单不存在");
        }
        validateUserPermission(todoList.getUserId());
        return todoList;
    }

    /**
     * 检查任务项ID是否存在并验证权限
     */
    private CloudTodoItemsDO checkItemId(String itemId) {
        CloudTodoItemsDO item = todoItemsMapper.selectByItemId(itemId);
        if (ObjectUtils.isEmpty(item)) {
            throw new BusinessException(OPERATE_FAILED, "任务项不存在");
        }
        // 通过清单ID验证权限
        CloudTodoListsDO todoList = todoListsMapper.selectByListId(item.getListId());
        validateUserPermission(todoList.getUserId());
        return item;
    }

    private void validateUserPermission(String userId) {
        String currentUserId = org.luckycloud.security.util.UserUtils.getUserId();
        if (!currentUserId.equals(userId)) {
            throw new BusinessException(OPERATE_FAILED, "无权限操作");
        }
    }
}
