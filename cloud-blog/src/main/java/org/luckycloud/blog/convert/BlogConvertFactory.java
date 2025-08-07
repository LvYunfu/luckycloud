package org.luckycloud.blog.convert;

import org.luckycloud.constant.SystemConstant;
import org.luckycloud.domain.blog.CloudBlogTagDO;

import java.util.List;

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
}
