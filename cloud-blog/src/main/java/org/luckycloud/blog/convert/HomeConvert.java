package org.luckycloud.blog.convert;

import org.luckycloud.blog.dto.request.HomeBlogQuery;
import org.luckycloud.blog.dto.response.BlogBaseResponse;
import org.luckycloud.blog.dto.response.BlogCategoryCountResponse;
import org.luckycloud.blog.dto.response.BlogInfoResponse;
import org.luckycloud.domain.blog.CloudBlogInfoDO;
import org.luckycloud.dto.blog.request.BlogQuery;
import org.luckycloud.dto.blog.response.CategoryCount;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/9
 */
@Mapper(componentModel = "spring")
public interface HomeConvert {


    BlogCategoryCountResponse toBlogCategoryCountVO(CategoryCount categoryCount);
    List<BlogCategoryCountResponse> toBlogCategoryCountVOList(List<CategoryCount> categoryCount);


    BlogQuery toBlogQuery(HomeBlogQuery categoryCount);

    BlogBaseResponse toBlogBase(CloudBlogInfoDO blogInfoDO);
    List<BlogBaseResponse> toBlogBaseList(List<CloudBlogInfoDO> blogInfoDOList);


}
