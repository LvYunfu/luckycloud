package org.luckycloud.blog.controller;

import jakarta.annotation.Resource;
import org.luckycloud.blog.service.BlogCategoryService;
import org.luckycloud.dto.common.Select;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/6
 */
@RestController
@RequestMapping("/blog-category")
public class BlogCategoryController {


    @Resource
    private BlogCategoryService blogCategoryService;

    /**
     * 功能描述：创建博客文章
     */
    @GetMapping("/get-category-list")
    public List<Select> getCategoryList(String categoryId) {
        return blogCategoryService.getCategoryList(categoryId);

    }
}
