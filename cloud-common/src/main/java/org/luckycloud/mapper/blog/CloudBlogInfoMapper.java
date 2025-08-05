package org.luckycloud.mapper.blog;

import org.luckycloud.domain.blog.CloudBlogInfoDO;

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

    CloudBlogInfoDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudBlogInfoDO record);

    int updateByPrimaryKey(CloudBlogInfoDO record);

}
