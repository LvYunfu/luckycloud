package org.luckycloud.blog.convert;

import org.luckycloud.constant.SystemConstant;
import org.luckycloud.domain.blog.CloudBlogTagDO;
import org.luckycloud.dto.blog.request.BlogOperateQuery;
import org.luckycloud.security.util.UserUtils;

import java.util.List;

import static org.luckycloud.constant.BlogConstant.BlogOperateType.COLLECT;
import static org.luckycloud.constant.BlogConstant.BlogOperateType.LIKE;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/6
 */
public class BlogConvertFactory {

    private BlogConvertFactory() {
    }
    public static List<CloudBlogTagDO> convertToBlogTagDOList(List<String> tags,String blogId) {
       return tags.stream().map(tag -> {
            CloudBlogTagDO tagDO = new CloudBlogTagDO();
            tagDO.setBlogId(blogId);
            tagDO.setTagName(tag);
            tagDO.setStatus(SystemConstant.ENABLE);
            return tagDO;
        }).toList();

    }

    public static BlogOperateQuery buildOperateQuery(String blogId) {
        BlogOperateQuery query = new BlogOperateQuery();
        query.setBlogId(blogId);
        query.setOperateType(List.of(LIKE,COLLECT));
        query.setUserId(UserUtils.getUserId());
        return query;
    }

    public static BlogOperateQuery buildLikeOperateQuery(String blogId) {
        BlogOperateQuery query = new BlogOperateQuery();
        query.setBlogId(blogId);
        query.setOperateType(List.of(LIKE));
        query.setUserId(UserUtils.getUserId());
        return query;
    }
}
