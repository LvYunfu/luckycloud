package org.luckycloud.ai.service;

import org.luckycloud.ai.dto.*;
import org.luckycloud.dto.common.PageResponse;

import java.util.List;

/**
 * 表情包系列与资产管理服务
 * @author lvyf
 * @date 2026/2/27
 */
public interface EmojiService {

    /**
     * 创建表情包系列
     * @param command 创建命令
     * @return 创建结果（系列ID + 表情包ID列表）
     */
    EmojiGroupCreateResponse createEmojiGroup(EmojiGroupCreateCommand command);

    /**
     * 更新表情包系列
     * @param command 更新命令
     */
    void updateEmojiGroup(EmojiGroupUpdateCommand command);

    /**
     * 删除表情包系列（级联删除系列下所有表情包）
     * @param emojiGroupId 系列ID
     */
    void deleteEmojiGroup(String emojiGroupId);

    /**
     * 获取单个表情包系列详情
     * @param emojiGroupId 系列ID
     * @return 系列详情
     */
    EmojiGroupResponse getEmojiGroup(String emojiGroupId);

    /**
     * 获取表情包系列列表（分页）
     * @param query 查询条件
     * @return 分页结果
     */
    PageResponse<EmojiGroupResponse> getEmojiGroupList(EmojiGroupListQuery query);

    /**
     * 创建表情包
     * @param command 创建命令
     * @return 表情包ID
     */
    String saveEmojiInfo(EmojiInfoCreateCommand command);

    /**
     * 批量创建表情包
     * @param commands 创建命令列表
     * @return 成功创建数量
     */
    int batchSaveEmojiInfo(List<EmojiInfoCreateCommand> commands);

    /**
     * 更新表情包
     * @param command 更新命令
     */
    void updateEmojiInfo(EmojiInfoUpdateCommand command);

    /**
     * 删除表情包
     * @param emojiId 表情包ID
     */
    void deleteEmojiInfo(String emojiId);

    /**
     * 获取单个表情包详情
     * @param emojiId 表情包ID
     * @return 表情包详情
     */
    EmojiInfoResponse getEmojiInfo(String emojiId);

    /**
     * 获取表情包资产列表（分页）
     * @param query 查询条件
     * @return 分页结果
     */
    PageResponse<EmojiInfoResponse> getEmojiInfoList(EmojiInfoListQuery query);

    /**
     * 批量生成表情包（异步）
     * @param commands 批量生成命令列表
     * @return 任务ID
     */
    void batchGenerateEmoji(List<BatchGenerateCommand> commands);

    /**
     * 重新生成单个表情包
     * @param request 重新生成请求
     */
    void regenerateEmoji(RegenerateRequest request);

    /**
     * 查询生成任务进度
     * @param taskId 任务ID
     * @return 任务进度
     */
    TaskProgressResponse getTaskProgress(String taskId);

    /**
     * AI扩写表情包系列提示词
     * @param request 扩写请求，包含IP描述、系列关键词、数量、风格
     * @return 扩写后的提示词列表（标题、情景、元素、提示词）
     */
    List<ExpandGroupPromptResponse> expandGroupPrompt(ExpandGroupPromptRequest request);
}
