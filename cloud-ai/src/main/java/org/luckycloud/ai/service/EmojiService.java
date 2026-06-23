package org.luckycloud.ai.service;

import jakarta.servlet.http.HttpServletResponse;
import org.luckycloud.ai.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2026/2/27
 */
public interface EmojiService {


    /**
     * 创建表情包系列
     */
    String createEmojiGroup(EmojiGroupCreateCommand command);

    /**
     * 更新表情包系列
     */
    void updateEmojiGroup(EmojiGroupUpdateCommand command);

    /**
     * 删除表情包系列
     */
    void deleteEmojiGroup(String emojiGroupId);

    /**
     * 获取单个表情包系列详情
     */
    EmojiGroupResponse getEmojiGroup(String emojiGroupId);

    /**
     * 获取表情包系列列表
     */
    List<EmojiGroupResponse> getEmojiGroupList();

    /**
     * 创建表情包
     */
    String saveEmojiInfo(EmojiInfoCreateCommand command);

    /**
     * 批量创建表情包
     */
    int batchSaveEmojiInfo(List<EmojiInfoCreateCommand> commands);

    /**
     * 更新表情包
     */
    void updateEmojiInfo(EmojiInfoUpdateCommand command);

    /**
     * 删除表情包
     */
    void deleteEmojiInfo(String emojiId);

    /**
     * 获取单个表情包详情
     */
    EmojiInfoResponse getEmojiInfo(String emojiId);

    /**
     * 获取表情包列表
     */
    List<EmojiInfoResponse> getEmojiInfoList(String emojiGroupId);


    /**
     * 创建表情IP
     */
    String saveEmojiIp(EmojiIpCreateCommand command);

    /**
     * 更新表情IP
     */
    void updateEmojiIp(EmojiIpUpdateCommand command);

    /**
     * 删除表情IP
     */
    void deleteEmojiIp(String ipId);

    /**
     * 获取单个表情IP详情
     */
    EmojiIpResponse getEmojiIp(String ipId);

    /**
     * 获取表情IP列表
     */
    List<EmojiIpResponse> getEmojiIpList();

    /**
     * 通过大模型创造主题IP并返回文件
     */
    void createIp(EmojiIpCreateCommand request, HttpServletResponse response);


    /**
     * 上传表情IP
     */
    String uploadIp(EmojiIpCreateCommand request, MultipartFile file);

    /**
     * 利用大模型生成表情包描述
     *
     * @param command
     * @return
     */
    List<EmojiInfoResponse> emojiDesCreate(EmojiDescCreateCommand command);

    /**
     * 利用大模型创造表情包
     *
     * @param list
     */
    void createEmojiInfo(List<EmojiInfoCreateCommand> list);

    String createIpDesc(EmojiIpCreateCommand request);
}
