package org.luckycloud.blog.controller;

import jakarta.annotation.Resource;
import org.luckycloud.blog.dto.request.BlogInfoCommand;
import org.luckycloud.blog.service.BlogService;
import org.luckycloud.dto.common.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.luckycloud.constant.BlogConstant.BlogStatus.DRAFT;
import static org.luckycloud.constant.BlogConstant.BlogStatus.PUBLIC;
import static org.luckycloud.constant.SystemConstant.N;

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
    public Response<Void> createBlog(@RequestBody BlogInfoCommand request) {
        request.setBlogStatus(N.equals(request.getPrivateFlag()) ? PUBLIC : DRAFT);
        blogService.createBlog(request);
        return Response.success("博客文章创建成功");
    }

    /**
     * 保存为草稿
     * @param request
     * @return
     */
    @PostMapping("/save-draft")
    public Response<Void> saveDraft(@RequestBody BlogInfoCommand request) {
        request.setBlogStatus(DRAFT);
        blogService.createBlog(request);
        return Response.success("博客文章创建成功");
    }
}
