package org.luckycloud.blog.service;

import org.luckycloud.blog.dto.request.BlogInfoCommand;
import org.luckycloud.blog.dto.request.BlogOperateCommand;
import org.luckycloud.blog.dto.request.CommentBlogCommand;
import org.luckycloud.blog.dto.request.CommentQuery;
import org.luckycloud.blog.dto.response.BlogCommentResponse;
import org.luckycloud.dto.common.PageResponse;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/5
 */
public interface BlogService {
    void createBlog(BlogInfoCommand request);

    void commentBlog(CommentBlogCommand request);

    PageResponse<BlogCommentResponse> getBlogComment(CommentQuery query);

    void likeComment(BlogOperateCommand command);
}
