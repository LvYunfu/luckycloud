package org.luckycloud.blog.dto.request;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/17
 */
@Data
public class CommentBlogCommand {

    private String blogId;

    private String content;

    private String parentCommentId;

    private String firstCommentId;


}
