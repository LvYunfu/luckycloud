package org.luckycloud.ai.service;

import org.luckycloud.ai.dto.ConfirmWechatRequest;
import org.luckycloud.ai.dto.CreateSyncTaskRequest;
import org.luckycloud.ai.dto.SyncTaskListQuery;
import org.luckycloud.ai.dto.SyncTaskResponse;
import org.luckycloud.dto.common.PageResponse;

/**
 * 平台同步管理服务
 * @author lvyf
 * @date 2026/2/27
 */
public interface EmojiSyncService {

    /**
     * 创建平台同步任务
     * @param request 创建请求
     * @return 任务ID
     */
    String createSyncTask(CreateSyncTaskRequest request);

    /**
     * 确认微信手动上传完成
     * @param request 确认请求
     */
    void confirmWechatUpload(ConfirmWechatRequest request);

    /**
     * 获取同步任务状态
     * @param taskId 任务ID
     * @return 任务状态
     */
    SyncTaskResponse getSyncTaskStatus(String taskId);

    /**
     * 获取我的同步任务列表（分页）
     * @param query 查询条件
     * @return 分页结果
     */
    PageResponse<SyncTaskResponse> getSyncTaskList(SyncTaskListQuery query);
}
