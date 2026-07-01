package org.luckycloud.ai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 创建平台同步任务请求
 */
@Data
public class CreateSyncTaskRequest {
    /**
     * 目标平台：wechat / douyin
     */
    @NotBlank(message = "目标平台不能为空")
    private String targetPlatform;

    /**
     * 关联系列 ID（按系列同步时传）
     */
    private String emojiGroupId;

    /**
     * 表情包 ID 数组（对应表结构中的 sync_id，按单张/多选同步时传）
     */
    private List<String> syncIds;

    /**
     * 同步元数据（名称、简介、标签等）
     */
    private Object metaData;
}
