package org.luckycloud.blog.convert;

import jakarta.annotation.Resource;
import org.luckycloud.blog.dto.request.*;
import org.luckycloud.blog.dto.response.BlogCommentResponse;
import org.luckycloud.blog.dto.response.BlogInfoResponse;
import org.luckycloud.blog.dto.response.BlogStaticsResponse;
import org.luckycloud.domain.blog.CloudBlogCommentsDO;
import org.luckycloud.domain.blog.CloudBlogInfoDO;
import org.luckycloud.domain.blog.CloudBlogOperateDO;
import org.luckycloud.domain.blog.CloudFollowUserDO;
import org.luckycloud.dto.blog.request.BlogCommentQuery;
import org.luckycloud.dto.blog.response.BlogStatics;
import org.luckycloud.utils.UploadUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.luckycloud.constant.SystemConstant.ENABLE;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/5
 */
@Mapper(componentModel = "spring")
public abstract class BlogConvert {

    @Resource
    protected UploadUtils uploadUtils;

    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = ENABLE)
    @Mapping(target = "userId", expression = "java(org.luckycloud.security.util.UserUtils.getUserId())")
    public abstract CloudBlogInfoDO convertToBlogDO(BlogInfoCommand request);


    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = ENABLE)
    @Mapping(target = "likeCount", constant = "0")
    @Mapping(target = "userId", expression = "java(org.luckycloud.security.util.UserUtils.getUserId())")
    @Mapping(target = "commentId", expression = "java(org.luckycloud.utils.GenerateIdUtils.generateId())")
    public abstract CloudBlogCommentsDO convertToBlogCommentsDO(CommentBlogCommand request);

    public abstract BlogCommentQuery toCommentQuery(CommentQuery query);

    public abstract List<BlogCommentResponse> toBlogCommentDOList(List<CloudBlogCommentsDO> commands);


    public abstract BlogCommentResponse convertToBlogCommentResponse(CloudBlogCommentsDO commentDO);


    @Mapping(target = "coverImage", expression = "java(uploadUtils.getFileUrl(blogInfoDO.getUserId(),\"blog\", blogInfoDO.getCoverImage()))")
    public abstract BlogInfoResponse toBlogInfo(CloudBlogInfoDO blogInfoDO);

    public abstract BlogStaticsResponse convertStatics(BlogStatics statics);

    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "userId", expression = "java(org.luckycloud.security.util.UserUtils.getUserId())")
    public abstract CloudBlogOperateDO convertToBlogOperateDO(BlogOperateCommand command);


    public abstract CloudFollowUserDO toFollowUserDO(BlogFollowCommand command);

}
