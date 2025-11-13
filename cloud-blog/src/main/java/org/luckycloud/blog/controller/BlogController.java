package org.luckycloud.blog.controller;

import jakarta.annotation.Resource;
import org.luckycloud.blog.dto.request.*;
import org.luckycloud.blog.dto.response.BlogCommentResponse;
import org.luckycloud.blog.dto.response.BlogInfoResponse;
import org.luckycloud.blog.dto.response.BlogStaticsResponse;
import org.luckycloud.blog.service.BlogService;
import org.luckycloud.dto.common.PageResponse;
import org.luckycloud.dto.common.Response;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * 上传文件
     */
    @PostMapping("/upload-file")
    public Response<String> uploadFile(MultipartFile file) {
        return Response.successData(blogService.uploadFile(file));
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
     * 功能描述：获取博客文章信息
     *
     * @param blogId
     * @return
     */
    @GetMapping("/get-blog-info")
    public BlogInfoResponse getBlogInfo(String blogId) {
        return blogService.getBlogInfo(blogId);
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
     * 功能描述：获取博客操作信息
     *
     * @param query
     * @return
     */
    @PostMapping("/get-statics")
    public BlogStaticsResponse getBlogStatics(@RequestBody BlogIdQuery query) {

        return blogService.getBlogStatics(query);

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
     *
     * @param command
     * @return
     */
    @PostMapping("/like-comment")
    public Response<Void> likeComment(@RequestBody BlogOperateCommand command) {
        blogService.likeComment(command);
        return Response.success("点赞成功");

    }

    /**
     * 功能描述：点赞博客
     *
     * @param command
     * @return
     */
    @PostMapping("/like-blog")
    public Response<Void> likeBlog(@RequestBody BlogOperateCommand command) {
        return Response.success(blogService.likeBlog(command));

    }


    /**
     * 功能描述：点击博客
     *
     * @param command
     * @return
     */
    @PostMapping("/view-blog")
    public void viewBlog(@RequestBody BlogOperateCommand command) {
        blogService.viewBlog(command);

    }


}
