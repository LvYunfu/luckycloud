package org.luckycloud.blog.dto.request;

import lombok.Data;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/5
 */
@Data
public class BlogInfoRequest {
    /**
     * 博客标题
     */
    private String blogTitle;

    /**
     * 博客摘要
     */
    private String blogAbstract;

    /**
     * 博客内容
     */
    private String blogContent;

    /**
     * 博客路径
     */
    private String slug;

    /**
     * 作者
     */
    private String userId;

    /**
     * 目录
     */
    private String categoryId;

    /**
     * 封面
     */
    private String coverImage;


    private List<String> tags;

    private String privateFlag;
    /**
     *
     * BS00 保存草稿  BS01 公开   BS02 私有
     */
    private String blogStatus;

}
