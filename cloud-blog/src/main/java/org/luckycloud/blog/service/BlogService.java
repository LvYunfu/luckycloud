package org.luckycloud.blog.service;

import org.luckycloud.blog.dto.request.*;
import org.luckycloud.blog.dto.response.BlogCommentResponse;
import org.luckycloud.blog.dto.response.BlogInfoResponse;
import org.luckycloud.blog.dto.response.BlogStaticsResponse;
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

    BlogInfoResponse getBlogInfo(String blogId);

    BlogStaticsResponse getBlogStatics(BlogIdQuery query);

    void likeBlog(BlogOperateCommand command);
}
