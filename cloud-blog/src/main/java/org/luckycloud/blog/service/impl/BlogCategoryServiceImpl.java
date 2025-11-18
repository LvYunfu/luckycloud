package org.luckycloud.blog.service.impl;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
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
        if (StringUtils.isBlank(categoryId)) {
            List<Select> first = categoriesDOList.stream().filter(e -> "0".equals(e.getParentId())).map(e -> new Select(e.getName(), e.getCategoryId())).toList();
            first.forEach(e -> {
                List<Select> children = categoriesDOList.stream()
                        .filter(child -> child.getParentId().equals(e.getValue()))
                        .map(child -> new Select(child.getName(), child.getCategoryId()))
                        .toList();
                e.setChildren(children);
            });
            return first;
        }
        return categoriesDOList.stream()
                .map(category -> new Select(category.getName(), category.getCategoryId()))
                .toList();
    }
}
