package org.luckycloud.dto.blog.response;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2025/9/1
 */
@Data
public class BlogStatics {

    private String blogId;

    /**
     * 点赞数
     */
    private int likeCount;

    /**
     * 浏览数
     */
    private int viewCount;

    /**
     * 收藏数
     */
    private int collectCount;
    /**
     * 评论数
     */
    private int commentCount;


}
