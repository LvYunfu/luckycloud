package org.luckycloud.blog.dto.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 活动记录响应
 * @author lvyf
 * @date 2026/4/11
 */
@Data
public class TodoActivityLogResponse {

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
    private List<String> imageUrls;

    /**
     * 地点
     */
    private String location;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
