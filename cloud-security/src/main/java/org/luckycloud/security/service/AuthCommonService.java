package org.luckycloud.security.service;

import jakarta.annotation.Resource;
import org.luckycloud.domain.common.CloudVerifyCodeInfoDO;
import org.luckycloud.exception.BusinessException;
import org.luckycloud.mapper.common.CloudVerifyCodeInfoMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.luckycloud.constant.SystemConstant.DISABLE;
import static org.luckycloud.dto.common.ResponseCode.OPERATE_FAILED;

/**
 * @author lvyf
 * @description:
 * @date 2025/12/15
 */
@Component
public class AuthCommonService {

    @Resource

    private CloudVerifyCodeInfoMapper verifyCodeInfoMapper;

    public void checkVerifyCode(String verifyId, String verifyCode) {

        CloudVerifyCodeInfoDO verifyCodeInfo = verifyCodeInfoMapper.selectByPrimaryKey(verifyId);
        if (Objects.isNull(verifyCodeInfo) || DISABLE.equals(verifyCodeInfo.getStatus())) {
            throw new BusinessException(OPERATE_FAILED, "验证码失效，请重新发送");
        }
        LocalDateTime sendTime = verifyCodeInfo.getSendTime();
        if (sendTime.plusSeconds(verifyCodeInfo.getExpireTime()).isBefore(LocalDateTime.now())) {
            throw new BusinessException(OPERATE_FAILED, "验证码过期，请重新发送");
        }
        if (!verifyCode.equals(verifyCodeInfo.getVerifyCode())) {
            throw new BusinessException(OPERATE_FAILED, "验证码错误");
        }
        verifyCodeInfoMapper.setVerifyCodeInvalid(verifyId);

    }
}
