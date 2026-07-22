package org.luckycloud.ai.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.luckycloud.ai.convert.EmojiGroupConvert;
import org.luckycloud.ai.convert.EmojiInfoConvert;
import org.luckycloud.ai.domain.AIRequest;
import org.luckycloud.ai.dto.*;
import org.luckycloud.ai.service.EmojiService;
import org.luckycloud.ai.service.PromptService;
import org.luckycloud.domain.emoji.CloudEmojiGroupDO;
import org.luckycloud.domain.emoji.CloudEmojiInfoDO;
import org.luckycloud.dto.common.PageResponse;
import org.luckycloud.dto.emoji.request.EmojiGroupQueryDTO;
import org.luckycloud.dto.emoji.request.EmojiInfoQueryDTO;
import org.luckycloud.mapper.emoji.CloudEmojiGroupMapper;
import org.luckycloud.mapper.emoji.CloudEmojiInfoMapper;
import org.luckycloud.security.util.UserUtils;
import org.luckycloud.utils.GenerateIdUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import static org.luckycloud.ai.config.PromptConfig.GROUP_EXPAND_DESCRIPTION;
import static org.luckycloud.ai.config.PromptConfig.GROUP_EXPAND_INPUT;
import static org.luckycloud.constant.SystemConstant.DISABLE;

/**
 * 表情包系列与资产管理服务实现
 * @author lvyf
 * @date 2026/2/27
 */
@Slf4j
@Service
public class EmojiServiceImpl implements EmojiService {


    @Resource
    private CloudEmojiGroupMapper emojiGroupMapper;

    @Resource
    private EmojiGroupConvert emojiGroupConvert;

    @Resource
    private CloudEmojiInfoMapper emojiInfoMapper;

    @Resource
    private EmojiInfoConvert emojiInfoConvert;

    @Resource(name = "syncThreadPool")
    private ThreadPoolExecutor syncThreadPool;

    @Resource
    private PromptService promptService;

    @Override
    public EmojiGroupCreateResponse createEmojiGroup(EmojiGroupCreateCommand command) {
        CloudEmojiGroupDO groupDO = emojiGroupConvert.convert2DO(command);
        // 生成唯一ID
        String groupId = GenerateIdUtils.generateId();
        groupDO.setEmojiGroupId(groupId);
        // 设置当前用户ID
        groupDO.setUserId(UserUtils.getUserId());
        emojiGroupMapper.insert(groupDO);

        // 构建响应
        EmojiGroupCreateResponse response = new EmojiGroupCreateResponse();
        response.setGroupId(groupId);

        // 同时保存表情包列表到 cloud_emoji_info 表
        List<EmojiInfoCreateCommand> emojiList = command.getEmojiList();
        if (emojiList != null && !emojiList.isEmpty()) {
            String userId = UserUtils.getUserId();
            List<String> emojiIds = new ArrayList<>();
            for (EmojiInfoCreateCommand emojiCommand : emojiList) {
                emojiCommand.setEmojiGroupId(groupId);
                emojiCommand.setIpId(command.getIpId());
                CloudEmojiInfoDO infoDO = emojiInfoConvert.convert2DO(emojiCommand);
                infoDO.setEmojiId(GenerateIdUtils.generateId());
                infoDO.setUserId(userId);
                infoDO.setGenerateStatus("processing");
                emojiInfoMapper.insert(infoDO);
                emojiIds.add(infoDO.getEmojiId());
            }
            response.setEmojiIds(emojiIds);
        }

        return response;
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
    public void batchGenerateEmoji(List<BatchGenerateCommand> commands) {
        // 异步执行生成
        syncThreadPool.execute(()->executeBatchGeneration(commands));
    }

    /**
     * 异步执行批量生成
     */
    public void executeBatchGeneration(List<BatchGenerateCommand> commands) {

        for (BatchGenerateCommand command : commands) {
            CloudEmojiInfoDO infoDO = new CloudEmojiInfoDO();
            // 生成成功，更新表情包记录
            infoDO.setEmojiId(command.getEmojiId());
            infoDO.setUpdateTime(LocalDateTime.now());
            try {
                // TODO: 调用图像生成模型（静态: 图生图 / 动态: 图生视频转GIF）
                // 根据系列的 emojiType 调用不同的生成接口
                String fileUrl = generateImage(command.getIpId(), command.getPromptText());
                infoDO.setFileUrl(fileUrl);
                infoDO.setGenerateStatus("success");
                emojiInfoMapper.updateByPrimaryKeySelective(infoDO);
            } catch (Exception e) {
                infoDO.setGenerateStatus("failed");
                log.error("表情包生成失败: {}", command.getTitle(), e);
            }
            emojiInfoMapper.updateByPrimaryKeySelective(infoDO);
        }

    }

    /**
     * 调用图像生成（预留接口）
     * TODO: 对接实际的图生图/图生视频模型
     */
    private String generateImage(String ipId, String promptText) {
        // TODO: 根据角色IP获取角色图片URL，结合提示词调用图像生成模型
        // 静态: 调用图生图模型 -> 返回 PNG URL
        // 动态: 调用图生视频模型 -> 转 GIF -> 返回 GIF URL
        return  "https://raw.githubusercontent.com/LvYunfu/lucky-picture/main/images/13/2_cfd86110b1ea470ba1c2543ea29e99f3.png";
    }

    @Override
    public void regenerateEmoji(RegenerateRequest request) {
        // 查询原表情包信息
        CloudEmojiInfoDO originalInfo = emojiInfoMapper.selectByPrimaryKey(request.getEmojiId());
        if (originalInfo == null) {
            throw new IllegalArgumentException("表情包不存在: " + request.getEmojiId());
        }
        // 使用原参数重新生成
        BatchGenerateCommand command = new BatchGenerateCommand();
        command.setEmojiId(originalInfo.getEmojiId());
        command.setEmojiGroupId(originalInfo.getEmojiGroupId());
        command.setIpId(originalInfo.getIpId());
        command.setTitle(originalInfo.getName());
        command.setPromptText(originalInfo.getPromptText());
        executeBatchGeneration( List.of(command));
    }


    @Override
    public List<ExpandGroupPromptResponse> expandGroupPrompt(ExpandGroupPromptRequest request) {
        // 构建AI请求
        AIRequest aiRequest = new AIRequest();
        // 使用表情包系列提示词扩写的系统提示
        aiRequest.setSystemPrompt(GROUP_EXPAND_DESCRIPTION);
        // 构建用户输入，将参数转换为模板所需格式
        aiRequest.setQuestion(promptService.buildPrompt(GROUP_EXPAND_INPUT, request));
        // 调用AI服务生成结构化提示词列表
//        List<ExpandGroupPromptResponse> items = aiSupportService.generateStructuredEntity(
//                aiRequest,
//                new ParameterizedTypeReference<>() {
//                });
        // 构建响应对象
        List<ExpandGroupPromptResponse> items = new ArrayList<>();
        for (int i = 0; i < request.getQuantity(); i++) {
            ExpandGroupPromptResponse item = new ExpandGroupPromptResponse();
            item.setTitle(request.getKeywords());
            item.setScenario(request.getStyle());
            item.setElements(request.getIpDescription());
            item.setPromptText(request.getKeywords() + " " + request.getStyle() + " " + request.getIpDescription());
            items.add(item);
        }

        return items;
    }
}
