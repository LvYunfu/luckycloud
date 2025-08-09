package org.luckycloud.blog.service.impl;

import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.luckycloud.blog.service.BlogCategoryService;
import org.luckycloud.domain.blog.CloudBlogCategoriesDO;
import org.luckycloud.dto.common.Select;
import org.luckycloud.mapper.blog.CloudBlogCategoriesMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/6
 */
@Service
public class BlogCategoryServiceImpl implements BlogCategoryService {

    @Resource
    private CloudBlogCategoriesMapper blogCategoriesMapper;

    @Override
    public List<Select> getCategoryList(String categoryId) {

        List<CloudBlogCategoriesDO> categoriesDOList = blogCategoriesMapper.selectCategoryListByParentId(categoryId);
        return categoriesDOList.stream()
                .map(category -> new Select(category.getName(), category.getCategoryId()))
                .toList();
    }
}
