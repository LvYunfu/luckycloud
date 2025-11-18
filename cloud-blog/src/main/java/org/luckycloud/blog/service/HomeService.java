package org.luckycloud.blog.service;

import org.luckycloud.blog.dto.request.HomeBlogQuery;
import org.luckycloud.blog.dto.response.BlogBaseResponse;
import org.luckycloud.blog.dto.response.BlogCategoryCountResponse;
import org.luckycloud.blog.dto.response.BlogInfoResponse;
import org.luckycloud.dto.common.PageResponse;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/9
 */
public interface HomeService {
    List<BlogCategoryCountResponse> getCategoryNum(String categoryId);

    PageResponse<BlogBaseResponse> getBlogList(HomeBlogQuery request);

    List<BlogBaseResponse> getHotBlogList();

}
