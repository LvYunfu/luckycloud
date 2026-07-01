package org.luckycloud.ai.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 生成任务进度响应
 */
@Data
public class TaskProgressResponse {
    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务状态：pending / processing / completed / failed
     */
    private String status;

    /**
     * 总数
     */
    private int total;

    /**
     * 已完成数
     */
    private int completed;

    /**
     * 失败数
     */
    private int failed;

    /**
     * 各子任务详情
     */
    private List<TaskItem> items;

    /**
     * 任务创建时间
     */
    private LocalDateTime createTime;

    @Data
    public static class TaskItem {
        /**
         * 表情名称
         */
        private String name;

        /**
         * 子任务状态：pending / success / failed
         */
        private String status;

        /**
         * 生成的表情包ID（成功时）
         */
        private String emojiId;

        /**
         * 生成的文件URL（成功时）
         */
        private String fileUrl;

        /**
         * 失败原因（失败时）
         */
        private String errorMsg;
    }
}
