package org.luckycloud.blog.convert;

import org.luckycloud.blog.dto.request.BlogInfoRequest;
import org.luckycloud.domain.blog.CloudBlogInfoDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
    CloudBlogInfoDO convertToBlogDO(BlogInfoRequest request);
}
