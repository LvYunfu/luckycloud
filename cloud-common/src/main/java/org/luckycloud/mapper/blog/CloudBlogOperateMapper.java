package org.luckycloud.mapper.blog;

import org.apache.ibatis.annotations.Param;
import org.luckycloud.domain.blog.CloudBlogInfoDO;
import org.luckycloud.domain.blog.CloudBlogOperateDO;
import org.luckycloud.dto.blog.request.BlogOperateQuery;
import org.luckycloud.dto.blog.response.BlogStatics;

import java.util.List;

/**
* @author lvyf
* @description 针对表【cloud_blog_operate】的数据库操作Mapper
* @createDate 2025-09-02 00:00:44
* @Entity org.luckycloud.domain.blog.CloudBlogOperateDO
*/
public interface CloudBlogOperateMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudBlogOperateDO record);

    int insertSelective(CloudBlogOperateDO record);

    CloudBlogOperateDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudBlogOperateDO record);

    int updateByPrimaryKey(CloudBlogOperateDO record);


    BlogStatics blogStatics(String blogId);

    List<BlogStatics> listBlogStatics(List<String> list);

    List<String> selectUserBlogOperate(@Param("query") BlogOperateQuery query);

    List<CloudBlogOperateDO> selectOperateRecord(@Param("query") BlogOperateQuery query);


    List<String> queryHotBlogList();
}
