package org.luckycloud.domain.todo;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 执行记录表
 * @TableName cloud_todo_items_activity_logs
 */
@Data
public class CloudTodoItemsActivityLogsDO {
    /**
     * 记录ID
     */
    private String logId;

    /**
     * 关联任务ID
     */
    private String itemId;

    /**
     * 此刻心得
     */
    private String contentText;

    /**
     * 图片URL列表
     */
    private String imageUrls;

    /**
     * 地点
     */
    private String location;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
