package org.luckycloud.blog.controller;

import jakarta.annotation.Resource;
import org.luckycloud.blog.dto.request.BlogQuery;
import org.luckycloud.blog.dto.response.BlogBaseResponse;
import org.luckycloud.blog.service.BlogListService;
import org.luckycloud.dto.common.PageResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lvyf
 * @description:
 * @date 2025/12/3
 */
@RestController
@RequestMapping("/personal-center")
public class PersonalCenterController {

    @Resource
    private BlogListService blogListService;

    /**
     * 获取个人博客列表
     *
     * @param request
     * @return
     */
    @PostMapping("/get-blog-list")
    public PageResponse<BlogBaseResponse> getBlogList(@RequestBody BlogQuery request) {
        return blogListService.getPersonalBlog(request);
    }

    /**
     * 获取收藏博客列表
     *
     * @param request
     * @return
     */
    @PostMapping("/get-collect-list")
    public PageResponse<BlogBaseResponse> getCollectList(@RequestBody BlogQuery request) {
        return blogListService.getCollectList(request);
    }

}
