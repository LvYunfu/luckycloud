package org.luckycloud.blog.controller;

import jakarta.annotation.Resource;
import org.luckycloud.blog.dto.request.BlogOperateCommand;
import org.luckycloud.blog.dto.request.HomeBlogQuery;
import org.luckycloud.blog.dto.response.BlogBaseResponse;
import org.luckycloud.blog.dto.response.BlogCategoryCountResponse;
import org.luckycloud.blog.dto.response.BlogInfoResponse;
import org.luckycloud.blog.service.HomeService;
import org.luckycloud.dto.common.PageResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/5
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @Resource
    private HomeService homeService;

    /**
     * 获取博客分类数量
     *
     * @return
     */
    @GetMapping("/get-category-num")
    public List<BlogCategoryCountResponse> getCategoryNum(String categoryId) {
        return homeService.getCategoryNum(categoryId);
    }

    /**
     * 获取博客列表
     *
     * @param request
     * @return
     */
    @PostMapping("/get-blog-list")
    public PageResponse<BlogBaseResponse> getBlogList(@RequestBody  HomeBlogQuery request) {
        return homeService.getBlogList(request);
    }

    @PostMapping("/get-hot-blog")
    public List<BlogBaseResponse> getHotBlog() {
       return  homeService.getHotBlogList();

    }
}
