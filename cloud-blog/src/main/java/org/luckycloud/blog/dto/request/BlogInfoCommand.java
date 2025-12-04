package org.luckycloud.blog.dto.request;

import lombok.Data;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/5
 */
@Data
public class BlogInfoCommand {
    /**
     * 博客标题
     */
    private String blogTitle;

    private String blogId;

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

    /**
     *
     * BS00 私有  BS01 公开
     */
    private String blogStatus;


}
