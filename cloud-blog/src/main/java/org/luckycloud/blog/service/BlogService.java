package org.luckycloud.blog.service;

import org.luckycloud.blog.dto.request.BlogInfoCommand;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/5
 */
public interface BlogService {
    void createBlog(BlogInfoCommand request);
}
