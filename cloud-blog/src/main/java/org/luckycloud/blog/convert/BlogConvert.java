package org.luckycloud.blog.convert;

import org.luckycloud.blog.dto.request.BlogInfoCommand;
import org.luckycloud.blog.dto.request.BlogOperateCommand;
import org.luckycloud.blog.dto.request.CommentBlogCommand;
import org.luckycloud.blog.dto.request.CommentQuery;
import org.luckycloud.blog.dto.response.BlogCommentResponse;
import org.luckycloud.blog.dto.response.BlogInfoResponse;
import org.luckycloud.blog.dto.response.BlogStaticsResponse;
import org.luckycloud.domain.blog.CloudBlogCommentsDO;
import org.luckycloud.domain.blog.CloudBlogInfoDO;
import org.luckycloud.domain.blog.CloudBlogOperateDO;
import org.luckycloud.dto.blog.request.BlogCommentQuery;
import org.luckycloud.dto.blog.response.BlogStatics;
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
public interface BlogConvert {

    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = ENABLE)
    @Mapping(target = "userId", expression = "java(org.luckycloud.security.util.UserUtils.getUserId())")
    CloudBlogInfoDO convertToBlogDO(BlogInfoCommand request);


    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = ENABLE)
    @Mapping(target = "likeCount", constant = "0")
    @Mapping(target = "userId", expression = "java(org.luckycloud.security.util.UserUtils.getUserId())")
    @Mapping(target = "commentId", expression = "java(org.luckycloud.utils.GenerateIdUtils.generateId())")
    CloudBlogCommentsDO convertToBlogCommentsDO(CommentBlogCommand request);

    BlogCommentQuery toCommentQuery(CommentQuery query);

    List<BlogCommentResponse> toBlogCommentDOList(List<CloudBlogCommentsDO> commands);


    BlogCommentResponse convertToBlogCommentResponse(CloudBlogCommentsDO commentDO);



    BlogInfoResponse toBlogInfo(CloudBlogInfoDO blogInfoDO);

    BlogStaticsResponse convertStatics(BlogStatics statics);
    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "userId", expression = "java(org.luckycloud.security.util.UserUtils.getUserId())")
    CloudBlogOperateDO convertToBlogOperateDO(BlogOperateCommand command);
}
