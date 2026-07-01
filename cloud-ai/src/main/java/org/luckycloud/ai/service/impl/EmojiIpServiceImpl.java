package org.luckycloud.ai.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.luckycloud.ai.convert.EmojiGroupConvert;
import org.luckycloud.ai.convert.EmojiInfoConvert;
import org.luckycloud.ai.convert.EmojiIpConvert;
import org.luckycloud.ai.domain.AIRequest;
import org.luckycloud.ai.dto.*;
import org.luckycloud.ai.service.AISupportService;
import org.luckycloud.ai.service.EmojiIpService;
import org.luckycloud.ai.service.EmojiService;
import org.luckycloud.ai.service.PromptService;
import org.luckycloud.domain.emoji.CloudEmojiGroupDO;
import org.luckycloud.domain.emoji.CloudEmojiInfoDO;
import org.luckycloud.domain.emoji.CloudEmojiIpInfoDO;
import org.luckycloud.dto.common.PageResponse;
import org.luckycloud.dto.emoji.request.EmojiIpListQueryDTO;
import org.luckycloud.mapper.emoji.CloudEmojiGroupMapper;
import org.luckycloud.mapper.emoji.CloudEmojiInfoMapper;
import org.luckycloud.mapper.emoji.CloudEmojiIpInfoMapper;
import org.luckycloud.security.util.UserUtils;
import org.luckycloud.utils.GenerateIdUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.luckycloud.ai.config.PromptConfig.*;
import static org.luckycloud.constant.SystemConstant.DISABLE;

/**
 * @author lvyf
 * @description:
 * @date 2026/2/27
 */
@Service
public class EmojiIpServiceImpl implements EmojiIpService {

    @Resource
    private ChatClient chatClient;

    @Resource
    private CloudEmojiGroupMapper emojiGroupMapper;

    @Resource
    private EmojiGroupConvert emojiGroupConvert;

    @Resource
    private CloudEmojiInfoMapper emojiInfoMapper;

    @Resource
    private EmojiInfoConvert emojiInfoConvert;

    @Resource
    private CloudEmojiIpInfoMapper emojiIpInfoMapper;

    @Resource
    private EmojiIpConvert emojiIpConvert;


    @Resource
    private AISupportService aiSupportService;

    @Resource
    private PromptService promptService;

    @Override
    public ExpandPromptResponse expandPrompt(ExpandPromptRequest request) {
        // 构建AI请求
        AIRequest aiRequest = new AIRequest();
        // 使用角色提示词扩写的系统提示
        aiRequest.setSystemPrompt(IP_DESCRIPTION);
        // 构建用户输入，将关键词转换为模板所需格式
        EmojiIpCreateCommand command = new EmojiIpCreateCommand();
        command.setName(request.getKeyword());
        command.setDescription(request.getKeyword());
        aiRequest.setQuestion(promptService.buildPrompt(IP_INPUT, command));
        // 调用AI服务生成提示词
        String promptText = aiSupportService.generateAnswer(aiRequest);
        // 构建响应对象
        ExpandPromptResponse response = new ExpandPromptResponse();
        response.setPromptText(promptText);
        return response;
    }

    @Override
    public String saveEmojiIp(EmojiIpCreateCommand command) {
        CloudEmojiIpInfoDO ipInfoDO = emojiIpConvert.convert2DO(command);
        // 生成唯一ID
        ipInfoDO.setIpId(GenerateIdUtils.generateId());
        String userId = UserUtils.getUserId();
        ipInfoDO.setUserId(userId);
        emojiIpInfoMapper.insert(ipInfoDO);
        return ipInfoDO.getIpId();
    }

    @Override
    public PageResponse<EmojiIpResponse> getEmojiIpList(EmojiIpListQuery query) {
        // 获取当前登录用户ID
        String userId = UserUtils.getUserId();

        // 设置用户ID到查询条件中
        query.setUserId(userId);
        PageMethod.startPage(query.getPageNum(), query.getPageSize());

        // 根据条件查询角色IP列表
        EmojiIpListQueryDTO queryDTO = emojiIpConvert.convert2QueryDto(query);
        List<CloudEmojiIpInfoDO> ipInfoDOList = emojiIpInfoMapper.selectIpList(queryDTO);

        // 获取分页信息
        PageInfo<CloudEmojiIpInfoDO> pageInfo = new PageInfo<>(ipInfoDOList);

        // 转换为响应对象
        List<EmojiIpResponse> responseList = emojiIpConvert.convert2ResponseList(ipInfoDOList);

        // 封装分页响应
        return new PageResponse<>(pageInfo.getTotal(), responseList);
    }

    @Override
    public void updateEmojiIp(EmojiIpUpdateCommand command) {
        CloudEmojiIpInfoDO ipInfoDO = emojiIpConvert.convert2DO(command);
        emojiIpInfoMapper.updateByPrimaryKeySelective(ipInfoDO);
    }

    @Override
    public void deleteEmojiIp(String ipId) {
        // 逻辑删除，将状态设置为无效
        CloudEmojiIpInfoDO ipInfoDO = new CloudEmojiIpInfoDO();
        ipInfoDO.setIpId(ipId);
        ipInfoDO.setStatus(DISABLE);
        ipInfoDO.setUpdateTime(LocalDateTime.now());
        emojiIpInfoMapper.updateByPrimaryKeySelective(ipInfoDO);
    }

}
