package org.luckycloud.blog.convert;

import org.luckycloud.blog.dto.request.BlogInfoCommand;
import org.luckycloud.blog.dto.request.CommentBlogCommand;
import org.luckycloud.domain.blog.CloudBlogCommentsDO;
import org.luckycloud.domain.blog.CloudBlogInfoDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.luckycloud.security.util.UserUtils;
import static org.luckycloud.constant.SystemConstant.ENABLE;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/5
 */
@Mapper(componentModel = "spring")
public interface BlogConvert {

    @Mapping(target = "createTime", expression = "java(new java.util.Date())")
    @Mapping(target = "updateTime", expression = "java(new java.util.Date())")
    @Mapping(target = "status", constant = ENABLE)
    @Mapping(target = "userId", expression = "java(UserUtils.getUserId())")
    CloudBlogInfoDO convertToBlogDO(BlogInfoCommand request);


    @Mapping(target = "createTime", expression = "java(new java.util.Date())")
    @Mapping(target = "updateTime", expression = "java(new java.util.Date())")
    @Mapping(target = "status", constant = ENABLE)
    @Mapping(target = "likeCount", constant = "0")
    @Mapping(target = "userId", expression = "java(UserUtils.getUserId())")
    CloudBlogCommentsDO convertToBlogCommentsDO(CommentBlogCommand request);
}
