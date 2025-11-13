package org.luckycloud.blog.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.kohsuke.github.GHContentUpdateResponse;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
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
import org.luckycloud.dto.blog.request.BlogOperateQuery;
import org.luckycloud.dto.blog.response.BlogStatics;
import org.luckycloud.dto.common.PageResponse;
import org.luckycloud.exception.BusinessException;
import org.luckycloud.mapper.blog.CloudBlogCommentsMapper;
import org.luckycloud.mapper.blog.CloudBlogInfoMapper;
import org.luckycloud.mapper.blog.CloudBlogOperateMapper;
import org.luckycloud.mapper.blog.CloudBlogTagMapper;
import org.luckycloud.security.util.UserUtils;
import org.luckycloud.utils.GenerateIdUtils;
import org.luckycloud.utils.TransactionalUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.luckycloud.constant.BlogConstant.BlogOperateType.*;
import static org.luckycloud.constant.BlogConstant.GITHUB_PATH;
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


    @Value("${lucky.cloud.blog-resource.github-token}")
    String gitHubToken;

    @Value("${lucky.cloud.blog-resource.file-path}")
    String filePath;

    @Value("${lucky.cloud.blog-resource.github-cdn}")
    String githubCdn;

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
        List<CloudBlogOperateDO>  operateList =  blogOperateMapper.selectOperateRecord(query);
        if(CollectionUtils.isEmpty(operateList)){
            CloudBlogOperateDO operateDO = blogConvert.convertToBlogOperateDO(command);
            operateDO.setOperateType(LIKE);
            blogOperateMapper.insert(operateDO);
        }else{
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
    public String uploadFile(MultipartFile file) {
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(gitHubToken).build();
            GHRepository repository = github.getRepository(filePath);
            String userId = UserUtils.getUserId();
            String path = String.join(GITHUB_PATH, List.of("images", userId, Optional.ofNullable(file.getOriginalFilename()).orElse(UUID.randomUUID().toString())));
            log.info("上传文件路径:{}", path);
            // 上传文件
            GHContentUpdateResponse updateResponse = repository.createContent()
                    .message("lucky blog upload userId:" + userId)
                    .path(path)
                    .content(file.getInputStream().readAllBytes())
                    .commit();

            return githubCdn + path;
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw new BusinessException(OPERATE_FAILED, "上传文件失败");
        }
    }


}
