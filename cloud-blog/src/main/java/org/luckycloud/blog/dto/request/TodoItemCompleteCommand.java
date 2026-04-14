package org.luckycloud.blog.dto.request;

import lombok.Data;

import java.util.List;

/**
 * 完成任务请求
 * @author lvyf
 * @date 2026/4/11
 */
@Data
public class TodoItemCompleteCommand {

    /**
     * 任务ID
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
}
