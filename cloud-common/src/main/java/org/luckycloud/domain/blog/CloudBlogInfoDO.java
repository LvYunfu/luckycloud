package org.luckycloud.domain.blog;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 *
 * @TableName cloud_blog_info
 */
@Data
public class CloudBlogInfoDO {
    /**
     * 博客ID
     */
    private String blogId;

    /**
     * 博客标题
     */
    private String blogTitle;

    /**
     * 博客摘要
     */
    private String blogAbstract;

    /**
     * 博客内容
     */
    private String blogContent;

    /**
     * 博客路径
     */
    private String slug;

    /**
     * 作者
     */
    private String userId;

    /**
     * 目录
     */
    private String categoryId;

    /**
     * 封面
     */
    private String coverImage;

    /**
     *  BS00 保存草稿  BS01 公开   BS02 私有
     */
    private String blogStatus;

    /**
     * 创作时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 0 有效 1无效
     */
    private String status;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CloudBlogInfoDO other = (CloudBlogInfoDO) that;
        return (this.getBlogId() == null ? other.getBlogId() == null : this.getBlogId().equals(other.getBlogId()))
            && (this.getBlogTitle() == null ? other.getBlogTitle() == null : this.getBlogTitle().equals(other.getBlogTitle()))
            && (this.getBlogAbstract() == null ? other.getBlogAbstract() == null : this.getBlogAbstract().equals(other.getBlogAbstract()))
            && (this.getBlogContent() == null ? other.getBlogContent() == null : this.getBlogContent().equals(other.getBlogContent()))
            && (this.getSlug() == null ? other.getSlug() == null : this.getSlug().equals(other.getSlug()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getCoverImage() == null ? other.getCoverImage() == null : this.getCoverImage().equals(other.getCoverImage()))
            && (this.getBlogStatus() == null ? other.getBlogStatus() == null : this.getBlogStatus().equals(other.getBlogStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBlogId() == null) ? 0 : getBlogId().hashCode());
        result = prime * result + ((getBlogTitle() == null) ? 0 : getBlogTitle().hashCode());
        result = prime * result + ((getBlogAbstract() == null) ? 0 : getBlogAbstract().hashCode());
        result = prime * result + ((getBlogContent() == null) ? 0 : getBlogContent().hashCode());
        result = prime * result + ((getSlug() == null) ? 0 : getSlug().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getCoverImage() == null) ? 0 : getCoverImage().hashCode());
        result = prime * result + ((getBlogStatus() == null) ? 0 : getBlogStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", blogId=").append(blogId);
        sb.append(", blogTitle=").append(blogTitle);
        sb.append(", blogAbstract=").append(blogAbstract);
        sb.append(", blogContent=").append(blogContent);
        sb.append(", slug=").append(slug);
        sb.append(", userId=").append(userId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", coverImage=").append(coverImage);
        sb.append(", blogStatus=").append(blogStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }
}
