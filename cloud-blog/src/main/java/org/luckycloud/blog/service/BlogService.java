package org.luckycloud.blog.service;

import org.luckycloud.blog.dto.request.BlogInfoRequest;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/5
 */
public interface BlogService {
    void createBlog(BlogInfoRequest request);
}
