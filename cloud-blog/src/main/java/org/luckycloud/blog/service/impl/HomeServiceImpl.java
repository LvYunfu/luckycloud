package org.luckycloud.blog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;
import jakarta.annotation.Resource;
import org.luckycloud.blog.convert.HomeConvert;
import org.luckycloud.blog.dto.request.BlogQuery;
import org.luckycloud.blog.dto.request.HostBlogQuery;
import org.luckycloud.blog.dto.response.BlogBaseResponse;
import org.luckycloud.blog.dto.response.BlogCategoryCountResponse;
import org.luckycloud.blog.service.HomeService;
import org.luckycloud.domain.blog.CloudBlogCategoriesDO;
import org.luckycloud.domain.blog.CloudBlogInfoDO;
import org.luckycloud.domain.blog.CloudBlogTagDO;
import org.luckycloud.dto.blog.response.BlogStatics;
import org.luckycloud.dto.blog.response.CategoryCount;
import org.luckycloud.dto.common.PageResponse;
import org.luckycloud.mapper.blog.*;
import org.luckycloud.security.util.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/9
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Resource
    private CloudBlogCategoriesMapper blogCategoriesMapper;


    @Resource
    private CloudBlogInfoMapper blogInfoMapper;


    @Resource
    private HomeConvert homeConvert;

    @Resource
    private CloudBlogTagMapper blogTagMapper;


    @Resource
    private CloudBlogOperateMapper blogOperateMapper;

    @Resource
    private CloudBlogCommentsMapper cloudBlogCommentsMapper;

    @Override
    public List<BlogCategoryCountResponse> getCategoryNum(String categoryId) {
        List<CloudBlogCategoriesDO> categoriesDOList = blogCategoriesMapper.selectCategoryListByParentId(categoryId);
        List<CategoryCount> categoryCountList = blogInfoMapper.calculateCategoryCount(categoriesDOList.stream().map(CloudBlogCategoriesDO::getCategoryId).toList());

        Map<String, Integer> countMap = categoryCountList.stream()
                .collect(Collectors.toMap(CategoryCount::getCategoryId, CategoryCount::getCount));
        List<CategoryCount> countList = categoriesDOList.stream().map(e -> {
            CategoryCount count = new CategoryCount();
            count.setCategoryId(e.getCategoryId());
            count.setName(e.getName());
            count.setCount(countMap.getOrDefault(e.getCategoryId(), 0));
            return count;
        }).toList();
        return homeConvert.toBlogCategoryCountVOList(countList);
    }

    @Override
    public PageResponse<BlogBaseResponse> getBlogList(BlogQuery request) {
        if (!ObjectUtils.isEmpty(request.getTagName())) {
            List<String> blogIds = blogTagMapper.selectBlogIdListByTagName(request.getTagName());
            if (ObjectUtils.isEmpty(blogIds)) {
                return new PageResponse<>(0L, List.of());
            }
            request.setBlogIdList(blogIds);
        }
        PageMethod.startPage(request.getPageNum(), request.getPageSize());
        List<CloudBlogInfoDO> blog = blogInfoMapper.getBlogList(homeConvert.toBlogQuery(request));
        Page<CloudBlogInfoDO> blogPage = (Page<CloudBlogInfoDO>) blog;
        List<BlogBaseResponse> baseResponses = homeConvert.toBlogBaseList(blogPage.getResult());
        if (ObjectUtils.isEmpty(baseResponses)) {
            return new PageResponse<>(0L, List.of());
        }
        //查询tag
        List<String> idList = baseResponses.stream().map(BlogBaseResponse::getBlogId).toList();
        List<CloudBlogTagDO> tagList = blogTagMapper.selectBlogTag(idList);
        Map<String, List<String>> tagMap = tagList.stream()
                .collect(Collectors.groupingBy(CloudBlogTagDO::getBlogId, Collectors.mapping(CloudBlogTagDO::getTagName, Collectors.toList())));
        //查询统计数据
        Map<String, BlogStatics> staticsMap = blogOperateMapper.listBlogStatics(idList).stream().collect(Collectors.toMap(BlogStatics::getBlogId, e -> e));
        // 查询评论数
        Map<String, Integer> commentCountMap = cloudBlogCommentsMapper.getListBlogComment(idList).stream().collect(Collectors.toMap(BlogStatics::getBlogId, BlogStatics::getCommentCount));
        baseResponses.forEach(e -> {
            e.setTags(tagMap.get(e.getBlogId()));
            BlogStatics statics = staticsMap.getOrDefault(e.getBlogId(), new BlogStatics());
            e.setLikeCount(statics.getLikeCount());
            e.setViewCount(statics.getViewCount());
            e.setCommentCount(commentCountMap.getOrDefault(e.getBlogId(), 0));

        });
        return new PageResponse<>(blogPage.getTotal(), baseResponses);
    }

    @Override
    public List<BlogBaseResponse> getHotBlogList(HostBlogQuery request) {
        List<String> hotBlogIds = blogOperateMapper.queryHotBlogList(homeConvert.toHotQuery(request));
        org.luckycloud.dto.blog.request.BlogQuery query = new org.luckycloud.dto.blog.request.BlogQuery();
        query.setBlogIdList(hotBlogIds);
        List<CloudBlogInfoDO> blog = blogInfoMapper.getBlogList(query);
        return homeConvert.toBlogBaseList(blog);

    }

    @Override
    public List<String> getHotTag(HostBlogQuery request) {
        return blogTagMapper.selectHotTag(homeConvert.toHotQuery(request));
    }

    @Override
    public PageResponse<BlogBaseResponse> getPersonalBlog(BlogQuery request) {
        request.setUserId(UserUtils.getUserId());
        return this.getBlogList(request);
    }
}
