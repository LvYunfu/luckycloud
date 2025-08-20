package org.luckycloud.blog.controller;

import jakarta.annotation.Resource;
import org.luckycloud.blog.dto.request.BlogInfoCommand;
import org.luckycloud.blog.dto.request.BlogOperateCommand;
import org.luckycloud.blog.dto.request.CommentBlogCommand;
import org.luckycloud.blog.dto.request.CommentQuery;
import org.luckycloud.blog.dto.response.BlogCommentResponse;
import org.luckycloud.blog.service.BlogService;
import org.luckycloud.dto.common.PageResponse;
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
     *
     * @param request
     * @return
     */
    @PostMapping("/save-draft")
    public Response<Void> saveDraft(@RequestBody BlogInfoCommand request) {
        request.setBlogStatus(DRAFT);
        blogService.createBlog(request);
        return Response.success("博客文章创建成功");
    }

    /**
     * 功能描述：评论博客文章
     *
     * @param request
     * @return
     */
    @PostMapping("/comment")
    public Response<Void> commentBlog(@RequestBody CommentBlogCommand request) {
        blogService.commentBlog(request);
        return Response.success("评论成功");
    }


    /**
     * 功能描述：获取博客文章评论
     *
     * @param query
     * @return
     */
    @PostMapping("/get-comment")
    public PageResponse<BlogCommentResponse> getBlogComment(@RequestBody CommentQuery query) {
        return blogService.getBlogComment(query);

    }

    /**
     * 功能描述：点赞评论
     * @param command
     * @return
     */
    @PostMapping("/like-comment")
    public Response<Void> likeComment(@RequestBody BlogOperateCommand command) {
         blogService.likeComment(command);
        return Response.success("点赞成功");

    }
}
