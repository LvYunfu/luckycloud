package org.luckycloud.mapper.blog;

import org.luckycloud.domain.blog.CloudBlogCommentsDO;

/**
* @author lvyf
* @description 针对表【cloud_blog_comments】的数据库操作Mapper
* @createDate 2025-08-18 23:58:45
* @Entity org.luckycloud.domain.blog.CloudBlogCommentsDO
*/
public interface CloudBlogCommentsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudBlogCommentsDO record);

    int insertSelective(CloudBlogCommentsDO record);

    CloudBlogCommentsDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudBlogCommentsDO record);

    int updateByPrimaryKey(CloudBlogCommentsDO record);

}
