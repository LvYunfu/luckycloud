package org.luckycloud.security.components.loginProcess.impl;


import jakarta.annotation.Resource;
import org.luckycloud.domain.user.CloudUserInfoDO;
import org.luckycloud.enums.LoginType;
import org.luckycloud.mapper.user.CloudUserInfoMapper;
import org.luckycloud.security.components.loginProvider.VerifyCodeAuthenticationToken;
import org.luckycloud.security.components.loginProcess.LoginTypeService;
import org.luckycloud.security.dto.LoginRequest;
import org.luckycloud.security.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.luckycloud.enums.LoginType.MAIL_VERIFICATION_CODE;

/**
 * @author lvyf
 * @description:
 * @date 2023/7/4 19:50
 */
@Service
public class MailVerifyCodeWayServiceImpl implements LoginTypeService {

    @Resource
    private CloudUserInfoMapper cloudUserInfoMapper;

    @Resource
    private AuthService authService;

    @Override
    public LoginType getLoginType() {
        return MAIL_VERIFICATION_CODE;
    }

    @Override
    public Authentication loginProcess(LoginRequest request) {
        String mail = request.getMail();
        CloudUserInfoDO userInfoDO = cloudUserInfoMapper.findByMail(mail);
        if (userInfoDO == null) {
            throw new UsernameNotFoundException("账户未注册");
        }
        request.setUserId(userInfoDO.getUserId());
        return new VerifyCodeAuthenticationToken(request);

    }
}
