package org.luckycloud.ai.controller;

import jakarta.annotation.Resource;
import org.luckycloud.ai.dto.*;
import org.luckycloud.ai.service.EmojiIpService;
import org.luckycloud.ai.service.EmojiService;
import org.luckycloud.ai.service.EmojiSyncService;
import org.luckycloud.dto.common.PageResponse;
import org.luckycloud.dto.common.Response;
import org.luckycloud.dto.common.UploadFileDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 表情包生成系统控制器
 *
 * @author lvyf
 * @date 2026/2/27
 */
@RestController
@RequestMapping("/emoji")
public class EmojiController {

    @Resource
    private EmojiService emojiService;

    @Resource
    private EmojiIpService emojiIpService;

    @Resource
    private EmojiSyncService emojiSyncService;

    // ==================== 角色管理模块 ====================

    /**
     * 2.1 AI 扩写角色提示词
     * URL: /emoji/ip/expand-prompt
     */
    @PostMapping("/ip/expand-prompt")
    public ExpandPromptResponse expandPrompt(@Validated @RequestBody ExpandPromptRequest request) {
        return emojiIpService.expandPrompt(request);
    }

    /**
     * 2.2 保存角色 IP
     * URL: /emoji/ip/save
     */
    @PostMapping("/ip/save")
    public Response<String> saveEmojiIp(@Validated @RequestBody EmojiIpCreateCommand command) {
        return Response.successData(emojiIpService.saveEmojiIp(command));
    }

    /**
     * 2.3 获取我的角色列表
     * URL: /emoji/ip/list
     */
    @PostMapping("/ip/list")
    public PageResponse<EmojiIpResponse> getEmojiIpList(@RequestBody EmojiIpListQuery query) {
        return emojiIpService.getEmojiIpList(query);
    }
    @PostMapping("/ip/statistic")
    public EmojiIpStatisticResponse statisticEmojiIp() {
        return emojiIpService.statisticEmojiIp();
    }

    /**
     * 2.4 更新角色信息
     * URL: /emoji/ip/update
     */
    @PostMapping("/ip/update")
    public void updateEmojiIp(@Validated @RequestBody EmojiIpUpdateCommand command) {
        emojiIpService.updateEmojiIp(command);
    }

    /**
     * 2.5 删除角色
     * URL: /emoji/ip/delete
     */
    @PostMapping("/ip/delete")
    public void deleteEmojiIp(@RequestBody DeleteRequest request) {
        emojiIpService.deleteEmojiIp(request.getId());
    }

    /**
     * 2.6 AI 生成图片
     * URL: /emoji/ip/generate-image
     */
    @PostMapping("/ip/generate-image")
    public GenerateImageResponse generateImage(@Validated @RequestBody GenerateImageRequest request) {
        GenerateImageResponse res=  new GenerateImageResponse();
        res.setFileName("测试");
        res.setFileUrl("https://raw.githubusercontent.com/LvYunfu/lucky-picture/main/images/13/2_cfd86110b1ea470ba1c2543ea29e99f3.png");
        return res;
//        return emojiIpService.generateImage(request);
    }

    // ==================== 表情包系列管理模块 ====================

    /**
     * 3.1 创建表情包系列
     * URL: /emoji/group/create
     */
    @PostMapping("/group/create")
    public Response<EmojiGroupCreateResponse> createEmojiGroup(@Validated @RequestBody EmojiGroupCreateCommand command) {
        return Response.successData(emojiService.createEmojiGroup(command));
    }

    /**
     * 3.2 更新表情包系列
     * URL: /emoji/group/update
     */
    @PostMapping("/group/update")
    public void updateEmojiGroup(@Validated @RequestBody EmojiGroupUpdateCommand command) {
        emojiService.updateEmojiGroup(command);
    }

    /**
     * 3.3 获取我的系列列表（分页）
     * URL: /emoji/group/list
     */
    @PostMapping("/group/list")
    public PageResponse<EmojiGroupResponse> getEmojiGroupList(@RequestBody EmojiGroupListQuery query) {
        return emojiService.getEmojiGroupList(query);
    }

    /**
     * 3.4 获取系列详情
     * URL: /emoji/group/detail
     */
    @PostMapping("/group/detail")
    public EmojiGroupResponse getEmojiGroupDetail(@RequestBody IdRequest request) {
        return emojiService.getEmojiGroup(request.getId());
    }

    /**
     * 3.5 删除表情包系列（级联删除系列下所有表情包）
     * URL: /emoji/group/delete
     */
    @PostMapping("/group/delete")
    public void deleteEmojiGroup(@RequestBody DeleteRequest request) {
        emojiService.deleteEmojiGroup(request.getId());
    }

    /**
     * 3.6 AI 扩写表情包系列提示词
     * URL: /emoji/group/expand-prompt
     */
    @PostMapping("/group/expand-prompt")
    public List<ExpandGroupPromptResponse> expandGroupPrompt(@Validated @RequestBody ExpandGroupPromptRequest request) {
        return emojiService.expandGroupPrompt(request);
    }

    // ==================== 表情包生成与资产管理模块 ====================

    /**
     * 4.1 批量生成并保存表情包（异步）
     * URL: /emoji/emoji/batch-generate
     */
    @PostMapping("/emoji/batch-generate")
    public Response<Void> batchGenerateEmoji(@Validated @RequestBody List<BatchGenerateCommand> commands) {
        emojiService.batchGenerateEmoji(commands);
        return Response.success("生成任务已开始");
    }

    /**
     * 4.2 重新生成单个表情包（异步）
     * URL: /emoji/emoji/regenerate
     */
    @PostMapping("/emoji/regenerate")
    public void regenerateEmoji(@Validated @RequestBody RegenerateRequest request) {
        emojiService.regenerateEmoji(request);
    }


    /**
     * 4.4 获取表情包资产列表（分页）
     * URL: /emoji/emoji/list
     */
    @PostMapping("/emoji/list")
    public PageResponse<EmojiInfoResponse> getEmojiInfoList(@RequestBody EmojiInfoListQuery query) {
        return emojiService.getEmojiInfoList(query);
    }

    /**
     * 4.5 获取表情包详情
     * URL: /emoji/emoji/detail
     */
    @PostMapping("/emoji/detail")
    public EmojiInfoResponse getEmojiInfoDetail(@RequestBody IdRequest request) {
        return emojiService.getEmojiInfo(request.getId());
    }

    /**
     * 4.6 更新表情包资产
     * URL: /emoji/emoji/update
     */
    @PostMapping("/emoji/update")
    public void updateEmojiInfo(@Validated @RequestBody EmojiInfoUpdateCommand command) {
        emojiService.updateEmojiInfo(command);
    }

    /**
     * 4.7 删除表情包资产
     * URL: /emoji/emoji/delete
     */
    @PostMapping("/emoji/delete")
    public void deleteEmojiInfo(@RequestBody DeleteBatchRequest request) {
        for (String emojiId : request.getIds()) {
            emojiService.deleteEmojiInfo(emojiId);
        }
    }

    // ==================== 平台同步模块 ====================

    /**
     * 5.1 创建平台同步任务
     * URL: /emoji/sync/create-task
     */
    @PostMapping("/sync/create-task")
    public Response<String> createSyncTask(@Validated @RequestBody CreateSyncTaskRequest request) {
        return Response.successData(emojiSyncService.createSyncTask(request));
    }

    /**
     * 5.2 确认微信手动上传完成
     * URL: /emoji/sync/confirm-wechat
     */
    @PostMapping("/sync/confirm-wechat")
    public void confirmWechatUpload(@Validated @RequestBody ConfirmWechatRequest request) {
        emojiSyncService.confirmWechatUpload(request);
    }

    /**
     * 5.3 获取同步任务状态
     * URL: /emoji/sync/task-status
     */
    @PostMapping("/sync/task-status")
    public SyncTaskResponse getSyncTaskStatus(@RequestBody IdRequest request) {
        return emojiSyncService.getSyncTaskStatus(request.getId());
    }

    /**
     * 5.4 获取我的同步任务列表
     * URL: /emoji/sync/task-list
     */
    @PostMapping("/sync/task-list")
    public PageResponse<SyncTaskResponse> getSyncTaskList(@RequestBody(required = false) SyncTaskListQuery query) {
        if (query == null) {
            query = new SyncTaskListQuery();
        }
        return emojiSyncService.getSyncTaskList(query);
    }

    // ==================== 通用基础模块 ====================

    /**
     * 6.1 文件上传
     * URL: /emoji/common/upload
     */
    @PostMapping("/common/upload")
    public UploadFileDTO uploadFile(@RequestParam("file") MultipartFile file) {
        return emojiIpService.uploadFile(file);
    }
}
