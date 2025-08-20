package org.luckycloud.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String userName;
    private String toUserId;
    private String toUserName;

    /**
     * 父评论ID
     */
    private String parentCommentId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer likeCount;


    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
