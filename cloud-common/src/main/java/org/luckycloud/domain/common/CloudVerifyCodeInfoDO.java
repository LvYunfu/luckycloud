package org.luckycloud.domain.common;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

/**
 *
 * @TableName cloud_verify_code_info
 */
@Data
public class CloudVerifyCodeInfoDO {
    /**
     * ID
     * -- GETTER --
     *  ID

     */
    private String verifyId;

    /**
     * 验证码
     * -- GETTER --
     *  验证码

     */
    private String verifyCode;

    /**
     * 用户ID
     * -- GETTER --
     *  用户ID

     */
    private String userId;

    /**
     * 用户IP
     * -- GETTER --
     *  用户IP

     */
    private String userIp;

    /**
     * 验证码用途
     * -- GETTER --
     *  验证码用途

     */
    private String verifyType;

    /**
     * 发送时间
     * -- GETTER --
     *  发送时间

     */
    private Date sendTime;

    /**
     * 过期时间 ms
     * -- GETTER --
     *  过期时间 ms

     */
    private Integer expireTime;

    /**
     *
     * -- GETTER --
     *

     */
    private Date createTime;

    /**
     *
     * -- GETTER --
     *

     */
    private Date updateTime;

    /**
     * 1 有效 0无效
     * -- GETTER --
     *  1 有效 0无效

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
        CloudVerifyCodeInfoDO other = (CloudVerifyCodeInfoDO) that;
        return (this.getVerifyId() == null ? other.getVerifyId() == null : this.getVerifyId().equals(other.getVerifyId()))
            && (this.getVerifyCode() == null ? other.getVerifyCode() == null : this.getVerifyCode().equals(other.getVerifyCode()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getUserIp() == null ? other.getUserIp() == null : this.getUserIp().equals(other.getUserIp()))
            && (this.getVerifyType() == null ? other.getVerifyType() == null : this.getVerifyType().equals(other.getVerifyType()))
            && (this.getSendTime() == null ? other.getSendTime() == null : this.getSendTime().equals(other.getSendTime()))
            && (this.getExpireTime() == null ? other.getExpireTime() == null : this.getExpireTime().equals(other.getExpireTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getVerifyId() == null) ? 0 : getVerifyId().hashCode());
        result = prime * result + ((getVerifyCode() == null) ? 0 : getVerifyCode().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUserIp() == null) ? 0 : getUserIp().hashCode());
        result = prime * result + ((getVerifyType() == null) ? 0 : getVerifyType().hashCode());
        result = prime * result + ((getSendTime() == null) ? 0 : getSendTime().hashCode());
        result = prime * result + ((getExpireTime() == null) ? 0 : getExpireTime().hashCode());
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
        sb.append(", verifyId=").append(verifyId);
        sb.append(", verifyCode=").append(verifyCode);
        sb.append(", userId=").append(userId);
        sb.append(", userIp=").append(userIp);
        sb.append(", verifyType=").append(verifyType);
        sb.append(", sendTime=").append(sendTime);
        sb.append(", expireTime=").append(expireTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }
}
