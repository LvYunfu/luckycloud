package org.luckycloud.blog.controller;

import jakarta.annotation.Resource;
import org.luckycloud.blog.dto.request.BlogInfoRequest;
import org.luckycloud.blog.service.BlogService;
import org.luckycloud.dto.common.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/5
 */

@RestController
@RequestMapping("/blog")
public class BlogController {


    @Resource
    private BlogService blogService;

    /**
     * 功能描述：创建博客文章
     */
    @PostMapping("/create")
    public Response<Void> createBlog(BlogInfoRequest request) {
        blogService.createBlog(request);
        return Response.success("博客文章创建成功");
    }
}
