package org.luckycloud.blog.service;

import org.luckycloud.blog.dto.request.BlogInfoCommand;
import org.luckycloud.blog.dto.request.CommentBlogCommand;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/5
 */
public interface BlogService {
    void createBlog(BlogInfoCommand request);

    void commentBlog(CommentBlogCommand request);
}
