package org.luckycloud.blog.convert;

import jakarta.annotation.Resource;
import org.luckycloud.blog.dto.request.HomeBlogQuery;
import org.luckycloud.blog.dto.request.HostBlogQuery;
import org.luckycloud.blog.dto.response.BlogBaseResponse;
import org.luckycloud.blog.dto.response.BlogCategoryCountResponse;
import org.luckycloud.blog.dto.response.BlogInfoResponse;
import org.luckycloud.domain.blog.CloudBlogInfoDO;
import org.luckycloud.dto.blog.request.BlogQuery;
import org.luckycloud.dto.blog.request.HotQuery;
import org.luckycloud.dto.blog.response.CategoryCount;
import org.luckycloud.utils.UploadUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/9
 */
@Mapper(componentModel = "spring")
public abstract class HomeConvert {

    @Resource
    protected UploadUtils uploadUtils;

    public abstract BlogCategoryCountResponse toBlogCategoryCountVO(CategoryCount categoryCount);

    public abstract List<BlogCategoryCountResponse> toBlogCategoryCountVOList(List<CategoryCount> categoryCount);


    public abstract BlogQuery toBlogQuery(HomeBlogQuery categoryCount);

    public abstract HotQuery toHotQuery(HostBlogQuery categoryCount);

    @Mapping(target = "coverImage", expression = "java(uploadUtils.getFileUrl(blogInfoDO.getUserId(), blogInfoDO.getCoverImage()))")
    public abstract BlogBaseResponse toBlogBase(CloudBlogInfoDO blogInfoDO);

    public abstract List<BlogBaseResponse> toBlogBaseList(List<CloudBlogInfoDO> blogInfoDOList);


}
