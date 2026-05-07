package org.luckycloud.ai.controller;

import jakarta.annotation.Resource;
import org.luckycloud.ai.dto.*;
import org.luckycloud.ai.service.EmojiService;
import org.luckycloud.dto.common.Response;
import org.springframework.web.bind.annotation.*;

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
    public Response<Void> updateEmojiGroup(@RequestBody EmojiGroupUpdateCommand command) {
        emojiService.updateEmojiGroup(command);
        return Response.success("表情包系列更新成功");
    }

    /**
     * 删除表情包系列
     */
    @PostMapping("/group/delete")
    public Response<Void> deleteEmojiGroup(@RequestParam String emojiGroupId) {
        emojiService.deleteEmojiGroup(emojiGroupId);
        return Response.success("表情包系列删除成功");
    }

    /**
     * 获取单个表情包系列详情
     */
    @GetMapping("/group/get")
    public Response<EmojiGroupResponse> getEmojiGroup(@RequestParam String emojiGroupId) {
        EmojiGroupResponse group = emojiService.getEmojiGroup(emojiGroupId);
        return Response.successData(group);
    }

    /**
     * 获取表情包系列列表
     */
    @GetMapping("/group/list")
    public Response<List<EmojiGroupResponse>> getEmojiGroupList() {
        List<EmojiGroupResponse> list = emojiService.getEmojiGroupList();
        return Response.successData(list);
    }

    /**
     * 创建表情包
     */
    @PostMapping("/emoji/create")
    public Response<String> createEmojiInfo(@RequestBody EmojiInfoCreateCommand command) {
        String emojiId = emojiService.createEmojiInfo(command);
        return Response.successData(emojiId, "表情包创建成功");
    }

    /**
     * 批量创建表情包
     */
    @PostMapping("/emoji/batch-create")
    public Response<Integer> batchCreateEmojiInfo(@RequestBody List<EmojiInfoCreateCommand> commands) {
        int count = emojiService.batchCreateEmojiInfo(commands);
        return Response.successData(count, "批量创建成功，共创建" + count + "个表情包");
    }

    /**
     * 更新表情包
     */
    @PostMapping("/emoji/update")
    public Response<Void> updateEmojiInfo(@RequestBody EmojiInfoUpdateCommand command) {
        emojiService.updateEmojiInfo(command);
        return Response.success("表情包更新成功");
    }

    /**
     * 删除表情包
     */
    @PostMapping("/emoji/delete")
    public Response<Void> deleteEmojiInfo(@RequestParam String emojiId) {
        emojiService.deleteEmojiInfo(emojiId);
        return Response.success("表情包删除成功");
    }

    /**
     * 获取单个表情包详情
     */
    @GetMapping("/emoji/get")
    public Response<EmojiInfoResponse> getEmojiInfo(@RequestParam String emojiId) {
        EmojiInfoResponse info = emojiService.getEmojiInfo(emojiId);
        return Response.successData(info);
    }

    /**
     * 获取表情包列表（按系列）
     */
    @GetMapping("/emoji/list")
    public Response<List<EmojiInfoResponse>> getEmojiInfoList(@RequestParam(required = false) String emojiGroupId) {
        List<EmojiInfoResponse> list = emojiService.getEmojiInfoList(emojiGroupId);
        return Response.successData(list);
    }

    /**
     * 上传emoji主题IP
     *
     * @param request
     * @return
     */
    @PostMapping("ip/upload")
    public String uploadIp(EmojiIpCreateCommand request) {
        return emojiService.uploadIp(request);

    }

    /**
     * 创建表情IP
     */
    @PostMapping("/ip/create")
    public Response<String> createEmojiIp(@RequestBody EmojiIpCreateCommand command) {
        String ipId = emojiService.createEmojiIp(command);
        return Response.successData(ipId, "表情IP创建成功");
    }

    /**
     * 更新表情IP
     */
    @PostMapping("/ip/update")
    public Response<Void> updateEmojiIp(@RequestBody EmojiIpUpdateCommand command) {
        emojiService.updateEmojiIp(command);
        return Response.success("表情IP更新成功");
    }

    /**
     * 删除表情IP
     */
    @PostMapping("/ip/delete")
    public Response<Void> deleteEmojiIp(@RequestParam String ipId) {
        emojiService.deleteEmojiIp(ipId);
        return Response.success("表情IP删除成功");
    }

    /**
     * 获取单个表情IP详情
     */
    @GetMapping("/ip/get")
    public Response<EmojiIpResponse> getEmojiIp(@RequestParam String ipId) {
        EmojiIpResponse ip = emojiService.getEmojiIp(ipId);
        return Response.successData(ip);
    }

    /**
     * 获取表情IP列表
     */
    @GetMapping("/ip/list")
    public Response<List<EmojiIpResponse>> getEmojiIpList() {
        List<EmojiIpResponse> list = emojiService.getEmojiIpList();
        return Response.successData(list);
    }
}
