package org.luckycloud.ai.service;

import jakarta.servlet.http.HttpServletResponse;
import org.luckycloud.ai.dto.*;
import org.luckycloud.dto.common.PageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2026/2/27
 */
public interface EmojiIpService {

    /**
     * AI扩写角色提示词
     * @param request 扩写请求，包含角色关键词
     * @return 扩写后的英文图像提示词响应对象
     */
    ExpandPromptResponse expandPrompt(ExpandPromptRequest request);


    /**
     * 保存角色 IP
     */
    String saveEmojiIp(EmojiIpCreateCommand command);


    /**
     * 获取表情IP列表
     * @param query 查询条件（名称、来源类型、分页）
     * @return 角色IP列表
     */
    PageResponse<EmojiIpResponse> getEmojiIpList(EmojiIpListQuery query);


    /**
     * 更新表情IP
     */
    void updateEmojiIp(EmojiIpUpdateCommand command);

    /**
     * 删除表情IP
     */
    void deleteEmojiIp(String ipId);

}
