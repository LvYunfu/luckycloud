package org.luckycloud.blog.controller;

import jakarta.annotation.Resource;
import org.luckycloud.blog.dto.request.BlogQuery;
import org.luckycloud.blog.dto.request.HostBlogQuery;
import org.luckycloud.blog.dto.response.BlogBaseResponse;
import org.luckycloud.blog.dto.response.BlogCategoryCountResponse;
import org.luckycloud.blog.service.BlogListService;
import org.luckycloud.dto.common.PageResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/5
 */
@RestController
@RequestMapping("/blog-list")
public class BlogListController {

    @Resource
    private BlogListService blogListService;

    /**
     * 获取博客分类数量
     *
     * @return
     */
    @GetMapping("/get-category-num")
    public List<BlogCategoryCountResponse> getCategoryNum(String categoryId) {
        return blogListService.getCategoryNum(categoryId);
    }

    /**
     * 获取博客列表
     *
     * @param request
     * @return
     */
    @PostMapping("/get-blog-list")
    public PageResponse<BlogBaseResponse> getBlogList(@RequestBody BlogQuery request) {
        return blogListService.getPublicBlogList(request);
    }

    @PostMapping("/get-hot-blog")
    public List<BlogBaseResponse> getHotBlog(@RequestBody HostBlogQuery request) {
       return  blogListService.getHotBlogList(request);
    }

    @PostMapping("/get-hot-tag")
    public List<String> getHotTag(@RequestBody HostBlogQuery request) {
        return  blogListService.getHotTag(request);

    }
}
