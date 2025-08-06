package org.luckycloud.blog.service;

import org.luckycloud.dto.common.Select;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/6
 */
public interface BlogCategoryService {
    List<Select> getCategoryList(String categoryId);
}
