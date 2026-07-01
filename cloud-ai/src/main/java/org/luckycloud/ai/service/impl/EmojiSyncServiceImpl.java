package org.luckycloud.ai.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.luckycloud.ai.dto.ConfirmWechatRequest;
import org.luckycloud.ai.dto.CreateSyncTaskRequest;
import org.luckycloud.ai.dto.SyncTaskListQuery;
import org.luckycloud.ai.dto.SyncTaskResponse;
import org.luckycloud.ai.service.EmojiSyncService;
import org.luckycloud.domain.emoji.CloudEmojiSyncTasksDO;
import org.luckycloud.dto.common.PageResponse;
import org.luckycloud.dto.emoji.request.SyncTaskQueryDTO;
import org.luckycloud.mapper.emoji.CloudEmojiSyncTasksMapper;
import org.luckycloud.security.util.UserUtils;
import org.luckycloud.utils.GenerateIdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 平台同步管理服务实现
 * @author lvyf
 * @date 2026/2/27
 */
@Slf4j
@Service
public class EmojiSyncServiceImpl implements EmojiSyncService {

    @Resource
    private CloudEmojiSyncTasksMapper syncTasksMapper;

    @Override
    public String createSyncTask(CreateSyncTaskRequest request) {
        String taskId = "sync_" + GenerateIdUtils.generateId();
        String userId = UserUtils.getUserId();
        LocalDateTime now = LocalDateTime.now();

        // 确定需要同步的表情包ID列表
        List<String> syncIds = new ArrayList<>();
        if (request.getSyncIds() != null && !request.getSyncIds().isEmpty()) {
            // 按单张/多选同步
            syncIds.addAll(request.getSyncIds());
        }

        if (syncIds.isEmpty() && request.getEmojiGroupId() != null) {
            // 按系列同步，syncId 使用系列ID标识
            syncIds.add(request.getEmojiGroupId());
        }

        // 为每个 syncId 创建一条同步任务记录
        for (String syncId : syncIds) {
            CloudEmojiSyncTasksDO taskDO = new CloudEmojiSyncTasksDO();
            taskDO.setTaskId(taskId);
            taskDO.setUserId(userId);
            taskDO.setEmojiGroupId(request.getEmojiGroupId());
            taskDO.setSyncId(syncId);
            taskDO.setTargetPlatform(request.getTargetPlatform());
            taskDO.setTaskStatus("pending");
            taskDO.setCreateTime(now);
            taskDO.setUpdateTime(now);
            syncTasksMapper.insert(taskDO);
        }

        // TODO: 根据目标平台调用异步同步逻辑（微信API / 抖音API）

        return taskId;
    }

    @Override
    public void confirmWechatUpload(ConfirmWechatRequest request) {
        CloudEmojiSyncTasksDO taskDO = syncTasksMapper.selectByTaskId(request.getTaskId());
        if (taskDO == null) {
            throw new IllegalArgumentException("同步任务不存在: " + request.getTaskId());
        }

        // 更新任务状态为 success
        taskDO.setTaskStatus("success");
        taskDO.setPlatformResponse("微信手动上传确认完成");
        taskDO.setUpdateTime(LocalDateTime.now());
        syncTasksMapper.updateByPrimaryKeySelective(taskDO);
    }

    @Override
    public SyncTaskResponse getSyncTaskStatus(String taskId) {
        CloudEmojiSyncTasksDO taskDO = syncTasksMapper.selectByTaskId(taskId);
        if (taskDO == null) {
            return null;
        }
        return convertToResponse(taskDO);
    }

    @Override
    public PageResponse<SyncTaskResponse> getSyncTaskList(SyncTaskListQuery query) {
        String userId = UserUtils.getUserId();

        // 分页查询
        PageMethod.startPage(query.getPageNum(), query.getPageSize());
        SyncTaskQueryDTO queryDTO = new SyncTaskQueryDTO();
        queryDTO.setUserId(userId);
        queryDTO.setTargetPlatform(query.getTargetPlatform());
        queryDTO.setTaskStatus(query.getTaskStatus());
        List<CloudEmojiSyncTasksDO> taskDOList = syncTasksMapper.selectSyncTaskList(queryDTO);

        PageInfo<CloudEmojiSyncTasksDO> pageInfo = new PageInfo<>(taskDOList);
        List<SyncTaskResponse> responseList = taskDOList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(pageInfo.getTotal(), responseList);
    }

    /**
     * DO 转 Response
     */
    private SyncTaskResponse convertToResponse(CloudEmojiSyncTasksDO taskDO) {
        SyncTaskResponse response = new SyncTaskResponse();
        BeanUtils.copyProperties(taskDO, response);
        return response;
    }
}
