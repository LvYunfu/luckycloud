package org.luckycloud.blog.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import jakarta.annotation.Resource;
import org.luckycloud.blog.convert.BlogConvert;
import org.luckycloud.blog.convert.BlogConvertFactory;
import org.luckycloud.blog.dto.request.*;
import org.luckycloud.blog.dto.response.BlogCommentResponse;
import org.luckycloud.blog.dto.response.BlogInfoResponse;
import org.luckycloud.blog.dto.response.BlogStaticsResponse;
import org.luckycloud.blog.service.BlogService;
import org.luckycloud.cache.UserInfoCache;
import org.luckycloud.domain.blog.CloudBlogCommentsDO;
import org.luckycloud.domain.blog.CloudBlogInfoDO;
import org.luckycloud.domain.blog.CloudBlogOperateDO;
import org.luckycloud.domain.blog.CloudBlogTagDO;
import org.luckycloud.dto.blog.request.BlogCommentQuery;
import org.luckycloud.dto.blog.response.BlogStatics;
import org.luckycloud.dto.common.PageResponse;
import org.luckycloud.mapper.blog.CloudBlogCommentsMapper;
import org.luckycloud.mapper.blog.CloudBlogInfoMapper;
import org.luckycloud.mapper.blog.CloudBlogOperateMapper;
import org.luckycloud.mapper.blog.CloudBlogTagMapper;
import org.luckycloud.utils.GenerateIdUtils;
import org.luckycloud.utils.TransactionalUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static org.luckycloud.constant.BlogConstant.BlogOperateType.LIKE;

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

    @Resource
    private CloudBlogOperateMapper blogOperateMapper;

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
    public BlogInfoResponse getBlogInfo(String blogId) {
        CloudBlogInfoDO blogInfoDO = blogInfoMapper.selectByBlogId(blogId);
        BlogInfoResponse blogInfo = blogConvert.toBlogInfo(blogInfoDO);
        blogInfo.setTags(blogTagMapper.selectBlogTag(List.of(blogId)).stream().map(CloudBlogTagDO::getTagName).toList());
        return blogInfo;
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
        if (!"0".equals(blogCommentQuery.getFirstCommentId())) {
            blogCommentQuery.setSortOrder("asc");
        }
        List<CloudBlogCommentsDO> comments = blogCommentsMapper.getBlogComment(blogCommentQuery);
        PageInfo<CloudBlogCommentsDO> commentPage = new PageInfo<>(comments);
        List<BlogCommentResponse> list = blogConvert.toBlogCommentDOList(comments);
        list.forEach(e -> {
            e.setUserName(userInfoCache.getUserName(e.getUserId()));
            if (!ObjectUtils.isEmpty(e.getToUserId())) {
                e.setToUserName(userInfoCache.getUserName(e.getToUserId()));
            }
        });
        return new PageResponse<>(commentPage.getTotal(), list);
    }

    @Override
    public void likeComment(BlogOperateCommand command) {
        blogCommentsMapper.likeComment(command.getCommentId());
    }

    @Override
    public BlogStaticsResponse getBlogStatics(BlogIdQuery query) {
        BlogStatics statics = blogOperateMapper.blogStatics(query.getBlogId());
        if (statics == null) {
            statics = new BlogStatics();
        }
        BlogStaticsResponse response = blogConvert.convertStatics(statics);
        response.setCommentCount(blogCommentsMapper.countCommentByBlogId(query.getBlogId()));
        return response;
    }

    @Override
    public void likeBlog(BlogOperateCommand command) {
        CloudBlogOperateDO operateDO = blogConvert.convertToBlogOperateDO(command);
        operateDO.setOperateType(LIKE);
        blogOperateMapper.insert(operateDO);
    }
}
