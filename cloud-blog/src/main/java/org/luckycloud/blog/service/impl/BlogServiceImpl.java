package org.luckycloud.blog.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.luckycloud.blog.convert.BlogConvert;
import org.luckycloud.blog.convert.BlogConvertFactory;
import org.luckycloud.blog.dto.request.*;
import org.luckycloud.blog.dto.response.BlogAuthorResponse;
import org.luckycloud.blog.dto.response.BlogCommentResponse;
import org.luckycloud.blog.dto.response.BlogInfoResponse;
import org.luckycloud.blog.dto.response.BlogStaticsResponse;
import org.luckycloud.blog.service.BlogService;
import org.luckycloud.cache.UserInfoCache;
import org.luckycloud.domain.blog.*;
import org.luckycloud.domain.user.CloudUserInfoDO;
import org.luckycloud.dto.blog.request.BlogCommentQuery;
import org.luckycloud.dto.blog.request.BlogOperateQuery;
import org.luckycloud.dto.blog.response.BlogStatics;
import org.luckycloud.dto.common.PageResponse;
import org.luckycloud.dto.common.UploadFileDTO;
import org.luckycloud.exception.BusinessException;
import org.luckycloud.mapper.blog.*;
import org.luckycloud.security.util.UserUtils;
import org.luckycloud.utils.GenerateIdUtils;
import org.luckycloud.utils.TransactionalUtils;
import org.luckycloud.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.luckycloud.constant.BlogConstant.BlogOperateType.*;
import static org.luckycloud.constant.SystemConstant.DISABLE;
import static org.luckycloud.constant.SystemConstant.ENABLE;
import static org.luckycloud.dto.common.ResponseCode.OPERATE_FAILED;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/5
 */
@Service
@Log4j2
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

    @Resource
    private CloudFollowUserMapper followUserMapper;



    @Override
    public void createBlog(BlogInfoCommand request) {

        CloudBlogInfoDO blogDO = blogConvert.convertToBlogDO(request);
        blogDO.setBlogId(GenerateIdUtils.generateId());
        List<CloudBlogTagDO> tagList = BlogConvertFactory.convertToBlogTagDOList(request.getTags(), blogDO.getBlogId(),blogDO.getCategoryId());

        transactionalUtils.executeInTransaction(List.of(
                () -> blogInfoMapper.insert(blogDO),
                () -> blogTagMapper.deleteBlogTag(blogDO.getBlogId()),
                () -> blogTagMapper.batchInsert(tagList)
        ));
    }

    @Override
    public void updateBlog(BlogInfoCommand request) {
        CloudBlogInfoDO blogDO = blogConvert.convertToBlogDO(request);
        List<CloudBlogTagDO> tagList = BlogConvertFactory.convertToBlogTagDOList(request.getTags(), blogDO.getBlogId(),blogDO.getCategoryId());

        transactionalUtils.executeInTransaction(List.of(
                () -> blogInfoMapper.updateByPrimaryKey(blogDO),
                () -> blogTagMapper.deleteBlogTag(blogDO.getBlogId()),
                () -> blogTagMapper.batchInsert(tagList)
        ));
    }

    @Override
    public BlogInfoResponse getBlogInfo(String blogId) {
        CloudBlogInfoDO blogInfoDO = blogInfoMapper.selectByBlogId(blogId);
        BlogInfoResponse blogInfo = blogConvert.toBlogInfo(blogInfoDO);
        blogInfo.setTags(blogTagMapper.selectBlogTag(List.of(blogId)).stream().map(CloudBlogTagDO::getTagName).toList());
        List<String> operateType = blogOperateMapper.selectUserBlogOperate(BlogConvertFactory.buildOperateQuery(blogId));
        blogInfo.setLikeFlag(operateType.contains(LIKE));
        blogInfo.setCollectFlag(operateType.contains(COLLECT));
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
    public String likeBlog(BlogOperateCommand command) {

        BlogOperateQuery query = BlogConvertFactory.buildLikeOperateQuery(command.getBlogId());
        List<CloudBlogOperateDO> operateList = blogOperateMapper.selectOperateRecord(query);
        if (CollectionUtils.isEmpty(operateList)) {
            CloudBlogOperateDO operateDO = blogConvert.convertToBlogOperateDO(command);
            operateDO.setOperateType(LIKE);
            blogOperateMapper.insert(operateDO);
        } else {
            CloudBlogOperateDO operateDO = new CloudBlogOperateDO();
            operateDO.setId(operateList.get(0).getId());
            operateDO.setStatus(command.getStatus());
            blogOperateMapper.updateByPrimaryKeySelective(operateDO);
        }

        return DISABLE.equals(command.getStatus()) ? "取消点赞" : "点赞成功";

    }

    @Override
    public void viewBlog(BlogOperateCommand command) {
        CloudBlogOperateDO operateDO = blogConvert.convertToBlogOperateDO(command);
        operateDO.setOperateType(VIEW);
        blogOperateMapper.insert(operateDO);
    }



    @Override
    public String followAuthor(BlogFollowCommand command) {
        command.setUserId(UserUtils.getUserId());
        CloudFollowUserDO followUserDO = blogConvert.toFollowUserDO(command);
        followUserMapper.insert(followUserDO);
        return command.getStatus().equals(ENABLE) ? "关注成功" : "取消关注";
    }


    @Override
    public BlogAuthorResponse getBlogAuthor(String userId) {
        BlogAuthorResponse response = new BlogAuthorResponse();
        CloudUserInfoDO cloudUserInfoDO = userInfoCache.getUserInfo(userId);
        response.setUserId(cloudUserInfoDO.getUserId());
        response.setUserName(cloudUserInfoDO.getUserName());
        response.setProfile(cloudUserInfoDO.getProfile());
        response.setFanCount(followUserMapper.countFollowFans(userId));
        response.setBlogCount(blogInfoMapper.countBlogByUserId(userId));
        response.setLikeCount(blogInfoMapper.countLikeByUserId(userId));
        response.setFollowFlag(followUserMapper.checkFollowUser(UserUtils.getUserId(),userId) > 0);
        return response;
    }
}
