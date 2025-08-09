package org.luckycloud.blog.dto.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/9
 */
@Data
public class BlogBaseResponse {

    /**
     * 博客ID
     */
    private String blogId;

    /**
     * 博客标题
     */
    private String blogTitle;

    /**
     * 博客摘要
     */
    private String blogAbstract;

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


    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 评论数
     */
    private int commentCount;

    /**
     * 点赞数
     */
    private int likeCount;

    /**
     * 阅读数
     */
    private int viewCount;

    /**
     * 标签
     */
    private List<String> tags;

}
