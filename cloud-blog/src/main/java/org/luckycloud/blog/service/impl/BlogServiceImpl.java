package org.luckycloud.blog.service.impl;

import jakarta.annotation.Resource;
import org.luckycloud.blog.convert.BlogConvert;
import org.luckycloud.blog.convert.BlogConvertFactory;
import org.luckycloud.blog.dto.request.BlogInfoRequest;
import org.luckycloud.blog.service.BlogService;
import org.luckycloud.domain.blog.CloudBlogInfoDO;
import org.luckycloud.domain.blog.CloudBlogTagDO;
import org.luckycloud.mapper.blog.CloudBlogInfoMapper;
import org.luckycloud.mapper.blog.CloudBlogTagMapper;
import org.luckycloud.utils.GenerateIdUtils;
import org.luckycloud.utils.TransactionalUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/5
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogConvert blogConvert;


    @Resource
    private CloudBlogInfoMapper blogInfoMapper;
    @Resource
    private CloudBlogTagMapper blogTagMapper;

    @Resource
    private TransactionalUtils transactionalUtils;
    @Override

    public void createBlog(BlogInfoRequest request) {

        CloudBlogInfoDO blogDO = blogConvert.convertToBlogDO(request);
        blogDO.setBlogId(GenerateIdUtils.generateId());
        List<CloudBlogTagDO> tagList = BlogConvertFactory.convertToBlogTagDOList(request.getTags(), blogDO.getBlogId());

        transactionalUtils.executeInTransaction(List.of(
                () -> blogInfoMapper.insert(blogDO),
                () -> blogTagMapper.batchInsert(tagList)
        ));
    }
}
