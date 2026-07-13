package org.luckycloud.ai.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static org.luckycloud.ai.config.PromptConfig.*;
import static org.luckycloud.constant.SystemConstant.DISABLE;

/**
 * @author lvyf
 * @description:
 * @date 2026/2/27
 */
@Slf4j
@Service
public class EmojiIpServiceImpl implements EmojiIpService {

    @Resource
    private CloudEmojiIpInfoMapper emojiIpInfoMapper;

    @Resource
    private EmojiIpConvert emojiIpConvert;


    @Resource
    private AISupportService aiSupportService;

    @Resource
    private PromptService promptService;

    @Resource
    private ImageModel imageModel;

    @Value("${lucky.cloud.file.upload-path:./data/images}")
    private String uploadPath;

    @Override
    public ExpandPromptResponse expandPrompt(ExpandPromptRequest request) {
        // 构建AI请求
        AIRequest aiRequest = new AIRequest();
        // 使用角色提示词扩写的系统提示
        aiRequest.setSystemPrompt(IP_DESCRIPTION);
        // 构建用户输入，将关键词转换为模板所需格式
        aiRequest.setQuestion(promptService.buildPrompt(IP_INPUT, request));
        // 调用AI服务生成提示词
//        ExpandPromptResponse response = aiSupportService.generateJsonEntity(aiRequest,ExpandPromptResponse.class);
        ExpandPromptResponse response = new ExpandPromptResponse();
        response.setPromptText(aiRequest.getQuestion());
        response.setDescription("IP_DESCRIPTION");
        return response;
    }

    @Override
    public String saveEmojiIp(EmojiIpCreateCommand command) {
        CloudEmojiIpInfoDO ipInfoDO = emojiIpConvert.convert2DO(command);
        // 生成唯一ID
        ipInfoDO.setIpId(GenerateIdUtils.generateId());
        String userId = UserUtils.getUserId();
        ipInfoDO.setUserId(userId);
        ipInfoDO.setIsDefault(0);
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

    @Override
    public GenerateImageResponse generateImage(GenerateImageRequest request) {
        // 1. 构建图片生成提示词
        ImagePrompt imagePrompt = new ImagePrompt(request.getPrompt());
        log.info("开始生成图片，提示词：{}", request.getPrompt());

        // 2. 调用大模型生成图片
        ImageResponse imageResponse = imageModel.call(imagePrompt);
        Image image = imageResponse.getResult().getOutput();

        // 3. 获取图片字节数据（支持 URL 和 Base64 两种返回格式）
        byte[] imageBytes;
        String url = image.getUrl();
        String b64Json = image.getB64Json();

        if (url != null && !url.isEmpty()) {
            // 从 URL 下载图片
            log.info("从URL下载图片：{}", url);
            imageBytes = downloadImageFromUrl(url);
        } else if (b64Json != null && !b64Json.isEmpty()) {
            // 从 Base64 解码图片
            log.info("从Base64解码图片数据");
            imageBytes = Base64.getDecoder().decode(b64Json);
        } else {
            log.error("图片生成失败：未获取到图片数据");
            throw new RuntimeException("图片生成失败：未获取到图片数据");
        }

        // 4. 生成文件ID并保存在本地
        String fileId = GenerateIdUtils.generateId();
        try {
            Path uploadDir = Paths.get(uploadPath);
            Files.createDirectories(uploadDir);
            Path filePath = uploadDir.resolve(fileId + ".png");
            Files.write(filePath, imageBytes);
            log.info("图片已保存至本地：{}", filePath.toAbsolutePath());
        } catch (IOException e) {
            log.error("保存图片失败", e);
            throw new RuntimeException("保存图片失败：" + e.getMessage(), e);
        }
        GenerateImageResponse response = GenerateImageResponse.builder().fileId(fileId).build();
        // 5. 返回文件ID
        return response;
    }

    /**
     * 从URL下载图片字节数据
     */
    private byte[] downloadImageFromUrl(String imageUrl) {
        try {
            return new URL(imageUrl).openStream().readAllBytes();
        } catch (IOException e) {
            log.error("下载图片失败：{}", imageUrl, e);
            throw new RuntimeException("下载图片失败：" + e.getMessage(), e);
        }
    }

}
