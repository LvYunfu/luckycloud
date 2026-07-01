package org.luckycloud.ai.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.luckycloud.ai.convert.EmojiGroupConvert;
import org.luckycloud.ai.convert.EmojiInfoConvert;
import org.luckycloud.ai.dto.*;
import org.luckycloud.ai.service.EmojiService;
import org.luckycloud.domain.emoji.CloudEmojiGroupDO;
import org.luckycloud.domain.emoji.CloudEmojiInfoDO;
import org.luckycloud.dto.common.PageResponse;
import org.luckycloud.dto.emoji.request.EmojiGroupQueryDTO;
import org.luckycloud.dto.emoji.request.EmojiInfoQueryDTO;
import org.luckycloud.mapper.emoji.CloudEmojiGroupMapper;
import org.luckycloud.mapper.emoji.CloudEmojiInfoMapper;
import org.luckycloud.security.util.UserUtils;
import org.luckycloud.utils.GenerateIdUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.luckycloud.constant.SystemConstant.DISABLE;

/**
 * 表情包系列与资产管理服务实现
 * @author lvyf
 * @date 2026/2/27
 */
@Slf4j
@Service
public class EmojiServiceImpl implements EmojiService {

    /**
     * 生成任务缓存（taskId -> TaskProgressResponse）
     */
    private final Map<String, TaskProgressResponse> taskCache = new ConcurrentHashMap<>();

    @Resource
    private CloudEmojiGroupMapper emojiGroupMapper;

    @Resource
    private EmojiGroupConvert emojiGroupConvert;

    @Resource
    private CloudEmojiInfoMapper emojiInfoMapper;

    @Resource
    private EmojiInfoConvert emojiInfoConvert;

    @Override
    public String createEmojiGroup(EmojiGroupCreateCommand command) {
        CloudEmojiGroupDO groupDO = emojiGroupConvert.convert2DO(command);
        // 生成唯一ID
        groupDO.setEmojiGroupId(GenerateIdUtils.generateId());
        // 设置当前用户ID
        groupDO.setUserId(UserUtils.getUserId());
        emojiGroupMapper.insert(groupDO);
        return groupDO.getEmojiGroupId();
    }

    @Override
    public void updateEmojiGroup(EmojiGroupUpdateCommand command) {
        CloudEmojiGroupDO groupDO = emojiGroupConvert.convert2DO(command);
        emojiGroupMapper.updateByPrimaryKeySelective(groupDO);
    }

    @Override
    public void deleteEmojiGroup(String emojiGroupId) {
        // 逻辑删除系列
        CloudEmojiGroupDO groupDO = new CloudEmojiGroupDO();
        groupDO.setEmojiGroupId(emojiGroupId);
        groupDO.setStatus(DISABLE);
        groupDO.setUpdateTime(LocalDateTime.now());
        emojiGroupMapper.updateByPrimaryKeySelective(groupDO);

        // 级联逻辑删除系列下所有表情包
        emojiInfoMapper.deleteByGroupId(emojiGroupId);
    }

    @Override
    public EmojiGroupResponse getEmojiGroup(String emojiGroupId) {
        CloudEmojiGroupDO groupDO = emojiGroupMapper.selectByPrimaryKey(emojiGroupId);
        if (groupDO == null) {
            return null;
        }
        EmojiGroupResponse response = emojiGroupConvert.convert2Response(groupDO);
        // 查询系列下表情包数量
        int count = emojiInfoMapper.countByGroupId(emojiGroupId);
        response.setEmojiCount(count);
        return response;
    }

    @Override
    public PageResponse<EmojiGroupResponse> getEmojiGroupList(EmojiGroupListQuery query) {
        // 获取当前登录用户ID
        String userId = UserUtils.getUserId();

        // 分页查询
        PageMethod.startPage(query.getPageNum(), query.getPageSize());
        EmojiGroupQueryDTO queryDTO = new EmojiGroupQueryDTO();
        queryDTO.setUserId(userId);
        queryDTO.setSeriesKeywords(query.getSeriesKeywords());
        queryDTO.setEmojiType(query.getEmojiType());
        queryDTO.setIpId(query.getIpId());
        List<CloudEmojiGroupDO> groupDOList = emojiGroupMapper.selectGroupList(queryDTO);

        PageInfo<CloudEmojiGroupDO> pageInfo = new PageInfo<>(groupDOList);
        List<EmojiGroupResponse> responseList = emojiGroupConvert.convert2ResponseList(groupDOList);

        // 填充每个系列的表情包数量
        for (EmojiGroupResponse response : responseList) {
            int count = emojiInfoMapper.countByGroupId(response.getEmojiGroupId());
            response.setEmojiCount(count);
        }

        return new PageResponse<>(pageInfo.getTotal(), responseList);
    }

    @Override
    public String saveEmojiInfo(EmojiInfoCreateCommand command) {
        CloudEmojiInfoDO infoDO = emojiInfoConvert.convert2DO(command);
        // 生成唯一ID
        infoDO.setEmojiId(GenerateIdUtils.generateId());
        // 设置当前用户ID
        infoDO.setUserId(UserUtils.getUserId());
        emojiInfoMapper.insert(infoDO);
        return infoDO.getEmojiId();
    }

    @Override
    public int batchSaveEmojiInfo(List<EmojiInfoCreateCommand> commands) {
        String userId = UserUtils.getUserId();
        List<CloudEmojiInfoDO> infoDOList = commands.stream()
                .map(command -> {
                    CloudEmojiInfoDO infoDO = emojiInfoConvert.convert2DO(command);
                    infoDO.setEmojiId(GenerateIdUtils.generateId());
                    infoDO.setUserId(userId);
                    return infoDO;
                })
                .collect(Collectors.toList());

        int count = 0;
        for (CloudEmojiInfoDO infoDO : infoDOList) {
            count += emojiInfoMapper.insert(infoDO);
        }
        return count;
    }

    @Override
    public void updateEmojiInfo(EmojiInfoUpdateCommand command) {
        CloudEmojiInfoDO infoDO = emojiInfoConvert.convert2DO(command);
        emojiInfoMapper.updateByPrimaryKeySelective(infoDO);
    }

    @Override
    public void deleteEmojiInfo(String emojiId) {
        // 逻辑删除
        CloudEmojiInfoDO infoDO = new CloudEmojiInfoDO();
        infoDO.setEmojiId(emojiId);
        infoDO.setStatus(DISABLE);
        infoDO.setUpdateTime(LocalDateTime.now());
        emojiInfoMapper.updateByPrimaryKeySelective(infoDO);
    }

    @Override
    public EmojiInfoResponse getEmojiInfo(String emojiId) {
        CloudEmojiInfoDO infoDO = emojiInfoMapper.selectByPrimaryKey(emojiId);
        if (infoDO == null) {
            return null;
        }
        return emojiInfoConvert.convert2Response(infoDO);
    }

    @Override
    public PageResponse<EmojiInfoResponse> getEmojiInfoList(EmojiInfoListQuery query) {
        // 获取当前登录用户ID
        String userId = UserUtils.getUserId();

        // 分页查询
        PageMethod.startPage(query.getPageNum(), query.getPageSize());
        EmojiInfoQueryDTO queryDTO = new EmojiInfoQueryDTO();
        queryDTO.setUserId(userId);
        queryDTO.setName(query.getName());
        queryDTO.setType(query.getType());
        queryDTO.setEmojiGroupId(query.getEmojiGroupId());
        queryDTO.setIpId(query.getIpId());
        queryDTO.setPromptText(query.getPromptText());
        List<CloudEmojiInfoDO> infoDOList = emojiInfoMapper.selectEmojiInfoList(queryDTO);

        PageInfo<CloudEmojiInfoDO> pageInfo = new PageInfo<>(infoDOList);
        List<EmojiInfoResponse> responseList = emojiInfoConvert.convert2ResponseList(infoDOList);

        return new PageResponse<>(pageInfo.getTotal(), responseList);
    }

    @Override
    public String batchGenerateEmoji(BatchGenerateRequest request) {
        String taskId = "task_" + GenerateIdUtils.generateId();
        String userId = UserUtils.getUserId();

        // 初始化任务进度
        TaskProgressResponse task = new TaskProgressResponse();
        task.setTaskId(taskId);
        task.setStatus("processing");
        task.setTotal(request.getDescriptions().size());
        task.setCompleted(0);
        task.setFailed(0);
        task.setCreateTime(LocalDateTime.now());

        List<TaskProgressResponse.TaskItem> items = new ArrayList<>();
        for (BatchGenerateRequest.DescriptionItem desc : request.getDescriptions()) {
            TaskProgressResponse.TaskItem item = new TaskProgressResponse.TaskItem();
            item.setName(desc.getName());
            item.setStatus("pending");
            items.add(item);
        }
        task.setItems(items);
        taskCache.put(taskId, task);

        // 异步执行生成
        executeBatchGeneration(taskId, request, userId);

        return taskId;
    }

    /**
     * 异步执行批量生成
     */
    @Async
    public void executeBatchGeneration(String taskId, BatchGenerateRequest request, String userId) {
        TaskProgressResponse task = taskCache.get(taskId);

        for (int i = 0; i < request.getDescriptions().size(); i++) {
            BatchGenerateRequest.DescriptionItem desc = request.getDescriptions().get(i);
            TaskProgressResponse.TaskItem item = task.getItems().get(i);

            try {
                item.setStatus("processing");

                // TODO: 调用图像生成模型（静态: 图生图 / 动态: 图生视频转GIF）
                // 根据系列的 emojiType 调用不同的生成接口
                String fileUrl = generateImage(request.getIpId(), desc.getPromptText());

                // 生成成功，保存表情包记录
                CloudEmojiInfoDO infoDO = new CloudEmojiInfoDO();
                infoDO.setEmojiId(GenerateIdUtils.generateId());
                infoDO.setUserId(userId);
                infoDO.setIpId(request.getIpId());
                infoDO.setEmojiGroupId(request.getEmojiGroupId());
                infoDO.setName(desc.getName());
                infoDO.setPromptText(desc.getPromptText());
                infoDO.setFileUrl(fileUrl);
                infoDO.setCreateTime(LocalDateTime.now());
                infoDO.setUpdateTime(LocalDateTime.now());
                infoDO.setStatus("1");
                emojiInfoMapper.insert(infoDO);

                item.setStatus("success");
                item.setEmojiId(infoDO.getEmojiId());
                item.setFileUrl(fileUrl);
                task.setCompleted(task.getCompleted() + 1);
            } catch (Exception e) {
                log.error("表情包生成失败: {}", desc.getName(), e);
                item.setStatus("failed");
                item.setErrorMsg(e.getMessage());
                task.setFailed(task.getFailed() + 1);
            }
        }

        // 更新任务状态
        task.setStatus(task.getFailed() == task.getTotal() ? "failed" : "completed");
    }

    /**
     * 调用图像生成（预留接口）
     * TODO: 对接实际的图生图/图生视频模型
     */
    private String generateImage(String ipId, String promptText) {
        // TODO: 根据角色IP获取角色图片URL，结合提示词调用图像生成模型
        // 静态: 调用图生图模型 -> 返回 PNG URL
        // 动态: 调用图生视频模型 -> 转 GIF -> 返回 GIF URL
        throw new UnsupportedOperationException("图像生成模型尚未对接");
    }

    @Override
    public void regenerateEmoji(RegenerateRequest request) {
        // 查询原表情包信息
        CloudEmojiInfoDO originalInfo = emojiInfoMapper.selectByPrimaryKey(request.getEmojiId());
        if (originalInfo == null) {
            throw new IllegalArgumentException("表情包不存在: " + request.getEmojiId());
        }

        // 逻辑删除原表情包
        deleteEmojiInfo(request.getEmojiId());

        // 使用原参数重新生成
        BatchGenerateRequest batchRequest = new BatchGenerateRequest();
        batchRequest.setEmojiGroupId(originalInfo.getEmojiGroupId());
        batchRequest.setIpId(originalInfo.getIpId());

        BatchGenerateRequest.DescriptionItem descItem = new BatchGenerateRequest.DescriptionItem();
        descItem.setName(originalInfo.getName());
        descItem.setPromptText(originalInfo.getPromptText());
        batchRequest.setDescriptions(List.of(descItem));

        // 异步重新生成
        String userId = UserUtils.getUserId();
        String taskId = "task_" + GenerateIdUtils.generateId();

        TaskProgressResponse task = new TaskProgressResponse();
        task.setTaskId(taskId);
        task.setStatus("processing");
        task.setTotal(1);
        task.setCompleted(0);
        task.setFailed(0);
        task.setCreateTime(LocalDateTime.now());

        TaskProgressResponse.TaskItem item = new TaskProgressResponse.TaskItem();
        item.setName(descItem.getName());
        item.setStatus("pending");
        task.setItems(List.of(item));
        taskCache.put(taskId, task);

        executeBatchGeneration(taskId, batchRequest, userId);
    }

    @Override
    public TaskProgressResponse getTaskProgress(String taskId) {
        return taskCache.get(taskId);
    }
}
