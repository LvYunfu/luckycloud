package org.luckycloud.blog.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/20
 */
@Data
public class BlogCommentResponse {
    /**
     * 评论ID
     */
    private String commentId;

    /**
     * 博客ID
     */
    private String blogId;

    /**
     * 评论人
     */
    private String userId;

    /**
     * 父评论ID
     */
    private Integer parentCommentId;

    /**
     *  评论内容
     */
    private String content;

    /**
     *   点赞数
     */
    private Integer likeCount;


    /**
     *
     */
    private LocalDateTime createTime;
}
