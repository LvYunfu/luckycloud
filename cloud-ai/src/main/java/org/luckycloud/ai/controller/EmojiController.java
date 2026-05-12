package org.luckycloud.ai.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.luckycloud.ai.dto.*;
import org.luckycloud.ai.service.EmojiService;
import org.luckycloud.dto.common.Response;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2026/2/27
 */

@RestController
@RequestMapping("/emoji")
public class EmojiController {


    @Resource
    private EmojiService emojiService;


    /**
     * 创建表情包系列
     */
    @PostMapping("/group/create")
    public Response<String> createEmojiGroup(@RequestBody EmojiGroupCreateCommand command) {
        String groupId = emojiService.createEmojiGroup(command);
        return Response.successData(groupId, "表情包系列创建成功");
    }

    /**
     * 更新表情包系列
     */
    @PostMapping("/group/update")
    public void updateEmojiGroup(@RequestBody EmojiGroupUpdateCommand command) {
        emojiService.updateEmojiGroup(command);
    }

    /**
     * 删除表情包系列
     */
    @PostMapping("/group/delete")
    public void deleteEmojiGroup(@RequestParam String emojiGroupId) {
        emojiService.deleteEmojiGroup(emojiGroupId);
    }

    /**
     * 获取单个表情包系列详情
     */
    @GetMapping("/group/get")
    public EmojiGroupResponse getEmojiGroup(@RequestParam String emojiGroupId) {
        return emojiService.getEmojiGroup(emojiGroupId);
    }

    /**
     * 获取表情包系列列表
     */
    @GetMapping("/group/list")
    public List<EmojiGroupResponse> getEmojiGroupList() {
        return emojiService.getEmojiGroupList();
    }


    /**
     * 利用大模型生成表情包描述
     */
    @PostMapping("/emoji/des-create")
    public List<EmojiInfoResponse> emojiDesCreate(@RequestBody EmojiDescCreateCommand command) {
        return emojiService.emojiDesCreate(command);
    }


    /**
     * 利用大模型创造表情包
     */
    @PostMapping("/emoji/create")
    public void createEmojiInfo(@RequestBody List<EmojiInfoCreateCommand> list) {
        emojiService.createEmojiInfo(list);
    }

    /**
     * 保存表情包
     */
    @PostMapping("/emoji/save")
    public Response<String> saveEmojiInfo(@RequestBody EmojiInfoCreateCommand command) {
        String emojiId = emojiService.saveEmojiInfo(command);
        return Response.successData(emojiId, "表情包保存成功");
    }

    /**
     * 批量保存表情包
     */
    @PostMapping("/emoji/batch-save")
    public Response<Void> batchCreateEmojiInfo(@RequestBody List<EmojiInfoCreateCommand> commands) {
        emojiService.batchSaveEmojiInfo(commands);
        return Response.success("表情包保存成功");
    }

    /**
     * 更新表情包
     */
    @PostMapping("/emoji/update")
    public void updateEmojiInfo(@RequestBody EmojiInfoUpdateCommand command) {
        emojiService.updateEmojiInfo(command);
    }

    /**
     * 删除表情包
     */
    @PostMapping("/emoji/delete")
    public void deleteEmojiInfo(@RequestParam String emojiId) {
        emojiService.deleteEmojiInfo(emojiId);
    }

    /**
     * 获取单个表情包详情
     */
    @GetMapping("/emoji/get")
    public EmojiInfoResponse getEmojiInfo(@RequestParam String emojiId) {
        return emojiService.getEmojiInfo(emojiId);
    }

    /**
     * 获取表情包列表（按系列）
     */
    @GetMapping("/emoji/list")
    public List<EmojiInfoResponse> getEmojiInfoList(@RequestParam(required = false) String emojiGroupId) {
        return emojiService.getEmojiInfoList(emojiGroupId);
    }


    /**
     * 通过大模型创造主题IP
     *
     * @param request
     * @return
     */
    @PostMapping("/ip/create")
    public void createIp(@Validated @RequestBody EmojiIpCreateCommand request, HttpServletResponse response) {
        emojiService.createIp(request, response);

    }


    /**
     * 上传emoji主题IP
     *
     * @param request
     * @return
     */
    @PostMapping("ip/upload")
    public Response<String> uploadIp(EmojiIpCreateCommand request, MultipartFile file) {
        String ipId = emojiService.uploadIp(request, file);
        return Response.successData(ipId, "主题IP上传成功");
    }

    /**
     * 创建表情IP
     */
    @PostMapping("/ip/create")
    public Response<String> saveEmojiIp(@RequestBody EmojiIpCreateCommand command) {
        String ipId = emojiService.saveEmojiIp(command);
        return Response.successData(ipId, "表情IP创建成功");
    }

    /**
     * 更新表情IP
     */
    @PostMapping("/ip/update")
    public void updateEmojiIp(@RequestBody EmojiIpUpdateCommand command) {
        emojiService.updateEmojiIp(command);
    }

    /**
     * 删除表情IP
     */
    @PostMapping("/ip/delete")
    public void deleteEmojiIp(@RequestParam String ipId) {
        emojiService.deleteEmojiIp(ipId);
    }

    /**
     * 获取单个表情IP详情
     */
    @GetMapping("/ip/get")
    public EmojiIpResponse getEmojiIp(@RequestParam String ipId) {
        return emojiService.getEmojiIp(ipId);
    }

    /**
     * 获取表情IP列表
     */
    @GetMapping("/ip/list")
    public List<EmojiIpResponse> getEmojiIpList() {
        return emojiService.getEmojiIpList();
    }
}
