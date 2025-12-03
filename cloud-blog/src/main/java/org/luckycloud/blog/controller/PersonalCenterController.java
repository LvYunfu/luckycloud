package org.luckycloud.blog.controller;

import jakarta.annotation.Resource;
import org.luckycloud.blog.dto.request.BlogQuery;
import org.luckycloud.blog.dto.response.BlogBaseResponse;
import org.luckycloud.blog.service.HomeService;
import org.luckycloud.dto.common.PageResponse;
import org.luckycloud.security.util.UserUtils;
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
    private HomeService homeService;

    /**
     * 获取个人博客列表
     *
     * @param request
     * @return
     */
    @PostMapping("/get-blog-list")
    public PageResponse<BlogBaseResponse> getBlogList(@RequestBody BlogQuery request) {
        return homeService.getPersonalBlog(request);
    }

}
