package org.luckycloud.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import jakarta.annotation.Resource;
import org.luckycloud.blog.convert.HomeConvert;
import org.luckycloud.blog.dto.request.HomeBlogQuery;
import org.luckycloud.blog.dto.response.BlogBaseResponse;
import org.luckycloud.blog.dto.response.BlogCategoryCountResponse;
import org.luckycloud.blog.dto.response.BlogInfoResponse;
import org.luckycloud.blog.service.HomeService;
import org.luckycloud.domain.blog.CloudBlogCategoriesDO;
import org.luckycloud.domain.blog.CloudBlogInfoDO;
import org.luckycloud.domain.blog.CloudBlogTagDO;
import org.luckycloud.dto.blog.request.BlogQuery;
import org.luckycloud.dto.blog.response.BlogStatics;
import org.luckycloud.dto.blog.response.CategoryCount;
import org.luckycloud.dto.common.PageResponse;
import org.luckycloud.mapper.blog.*;
import org.springframework.core.env.Environment;
import org.springframework.data.relational.core.sql.In;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
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
    public List<BlogCategoryCountResponse> getCategoryNum() {
        List<CategoryCount> categoryCountList = blogInfoMapper.calculateCategoryCount();
        List<String> categoryIds = categoryCountList.stream()
                .map(CategoryCount::getCategoryId)
                .toList();
        List<CloudBlogCategoriesDO> categoriesDOList = blogCategoriesMapper.selectCategoryListById(categoryIds);
        Map<String, String> categoryNameMap = categoriesDOList.stream()
                .collect(Collectors.toMap(CloudBlogCategoriesDO::getCategoryId, CloudBlogCategoriesDO::getName));
        categoryCountList.forEach(e -> e.setName(categoryNameMap.get(e.getCategoryId())));
        return homeConvert.toBlogCategoryCountVOList(categoryCountList);
    }
    @Override
    public PageResponse<BlogBaseResponse> getBlogList(HomeBlogQuery request) {
        if (!ObjectUtils.isEmpty(request.getTagName())) {
            List<String> blogIds = blogTagMapper.selectBlogIdListByTagName(request.getTagName());
            if (ObjectUtils.isEmpty(blogIds)) {
                return new PageResponse<>(0L, List.of());
            }
            request.setBlogIdList(blogIds);
        }
        PageMethod.startPage(request.getPageNum(), request.getPageSize());
        List<CloudBlogInfoDO> blog = blogInfoMapper.getBlogList(homeConvert.toBlogQuery(request));
        PageInfo<CloudBlogInfoDO> blogPage = new PageInfo<>(blog);
        List<BlogBaseResponse> baseResponses = homeConvert.toBlogBaseList(blogPage.getList());
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
    public List<BlogBaseResponse> getHotBlogList() {
        List<String> hotBlogIds = blogOperateMapper.queryHotBlogList();
        BlogQuery query = new BlogQuery();
        query.setBlogIdList(hotBlogIds);
        List<CloudBlogInfoDO> blog = blogInfoMapper.getBlogList(query);
        return homeConvert.toBlogBaseList(blog);

    }


}
