package org.luckycloud.mapper.blog;

import org.apache.ibatis.annotations.Param;
import org.luckycloud.domain.blog.CloudBlogTagDO;
import org.luckycloud.dto.blog.request.HotQuery;

import java.util.List;

/**
* @author lvyf
* @description 针对表【cloud_blog_tag】的数据库操作Mapper
* @createDate 2025-08-05 23:29:54
* @Entity org.luckycloud.domain.blog.CloudBlogTagDO
*/
public interface CloudBlogTagMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudBlogTagDO record);

    int insertSelective(CloudBlogTagDO record);


    int updateByPrimaryKeySelective(CloudBlogTagDO record);

    int updateByPrimaryKey(CloudBlogTagDO record);

    int batchInsert(List<CloudBlogTagDO> tagList);

     void deleteBlogTag(String blogId);

    List<String> selectBlogIdListByTagName(String tagName);

    List<CloudBlogTagDO> selectBlogTag(List<String> list);

    List<String> selectHotTag(@Param("query") HotQuery query);
}
