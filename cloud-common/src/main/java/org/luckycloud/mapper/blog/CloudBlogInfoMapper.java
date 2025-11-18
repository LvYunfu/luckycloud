package org.luckycloud.mapper.blog;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.luckycloud.domain.blog.CloudBlogInfoDO;
import org.luckycloud.dto.blog.request.BlogQuery;
import org.luckycloud.dto.blog.response.CategoryCount;

import java.util.List;

/**
* @author lvyf
* @description 针对表【cloud_blog_info】的数据库操作Mapper
* @createDate 2025-08-06 00:09:44
* @Entity org.luckycloud.domain.blog.CloudBlogInfoDO
*/
public interface CloudBlogInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudBlogInfoDO record);

    int insertSelective(CloudBlogInfoDO record);

    CloudBlogInfoDO selectByBlogId(String  blogId);

    int updateByPrimaryKeySelective(CloudBlogInfoDO record);

    int updateByPrimaryKey(CloudBlogInfoDO record);

    List<CategoryCount> calculateCategoryCount(@Param("list") List<String> categoryId);

    List<CloudBlogInfoDO> getBlogList(@Param("query") BlogQuery query);
}
