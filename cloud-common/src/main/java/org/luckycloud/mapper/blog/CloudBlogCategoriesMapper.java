package org.luckycloud.mapper.blog;

import org.luckycloud.domain.blog.CloudBlogCategoriesDO;

import java.util.List;

/**
* @author lvyf
* @description 针对表【cloud_blog_categories】的数据库操作Mapper
* @createDate 2025-08-06 21:02:06
* @Entity org.luckycloud.domain.blog.CloudBlogCategoriesDO
*/
public interface CloudBlogCategoriesMapper {


    int insert(CloudBlogCategoriesDO record);

    int insertSelective(CloudBlogCategoriesDO record);

    int updateByPrimaryKeySelective(CloudBlogCategoriesDO record);

    int updateByPrimaryKey(CloudBlogCategoriesDO record);

    List<CloudBlogCategoriesDO> getCategoryListByParentId(String categoryId);

}
