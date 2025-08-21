package org.luckycloud.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import jakarta.annotation.Resource;
import org.luckycloud.blog.convert.BlogConvert;
import org.luckycloud.blog.convert.BlogConvertFactory;
import org.luckycloud.blog.dto.request.BlogInfoCommand;
import org.luckycloud.blog.dto.request.BlogOperateCommand;
import org.luckycloud.blog.dto.request.CommentBlogCommand;
import org.luckycloud.blog.dto.request.CommentQuery;
import org.luckycloud.blog.dto.response.BlogCommentResponse;
import org.luckycloud.blog.service.BlogService;
import org.luckycloud.cache.UserInfoCache;
import org.luckycloud.domain.blog.CloudBlogCommentsDO;
import org.luckycloud.domain.blog.CloudBlogInfoDO;
import org.luckycloud.domain.blog.CloudBlogTagDO;
import org.luckycloud.dto.blog.request.BlogCommentQuery;
import org.luckycloud.dto.common.PageResponse;
import org.luckycloud.mapper.blog.CloudBlogCommentsMapper;
import org.luckycloud.mapper.blog.CloudBlogInfoMapper;
import org.luckycloud.mapper.blog.CloudBlogTagMapper;
import org.luckycloud.utils.GenerateIdUtils;
import org.luckycloud.utils.TransactionalUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/5
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogConvert blogConvert;


    @Resource
    private CloudBlogInfoMapper blogInfoMapper;
    @Resource
    private CloudBlogTagMapper blogTagMapper;

    @Resource
    private TransactionalUtils transactionalUtils;

    @Resource
    private CloudBlogCommentsMapper blogCommentsMapper;


    @Resource
    private UserInfoCache userInfoCache;

    @Override

    public void createBlog(BlogInfoCommand request) {

        CloudBlogInfoDO blogDO = blogConvert.convertToBlogDO(request);
        blogDO.setBlogId(GenerateIdUtils.generateId());
        List<CloudBlogTagDO> tagList = BlogConvertFactory.convertToBlogTagDOList(request.getTags(), blogDO.getBlogId());

        transactionalUtils.executeInTransaction(List.of(
                () -> blogInfoMapper.insert(blogDO),
                () -> blogTagMapper.deleteBlogTag(blogDO.getBlogId()),
                () -> blogTagMapper.batchInsert(tagList)
        ));
    }

    @Override
    public void commentBlog(CommentBlogCommand request) {
        CloudBlogCommentsDO blogCommentsDO = blogConvert.convertToBlogCommentsDO(request);
        blogCommentsMapper.insert(blogCommentsDO);
    }

    @Override
    public PageResponse<BlogCommentResponse> getBlogComment(CommentQuery query) {
        PageMethod.startPage(query.getPageNum(), query.getPageSize());
        BlogCommentQuery blogCommentQuery = blogConvert.toCommentQuery(query);
        List<CloudBlogCommentsDO> comments = blogCommentsMapper.getBlogComment(blogCommentQuery);
        PageInfo<CloudBlogCommentsDO> commentPage = new PageInfo<>(comments);
        List<BlogCommentResponse> list = blogConvert.toBlogCommentDOList(comments);
        list.forEach(e -> {
            e.setUserName(userInfoCache.getUserName(e.getUserId()));
            if( !ObjectUtils.isEmpty(e.getToUserId())) {
                e.setToUserName(userInfoCache.getUserName(e.getUserId()));
            }
        });
        return new PageResponse<>(commentPage.getTotal(), list);
    }

    @Override
    public void likeComment(BlogOperateCommand command) {
        blogCommentsMapper.likeComment(command.getCommentId());
    }
}
