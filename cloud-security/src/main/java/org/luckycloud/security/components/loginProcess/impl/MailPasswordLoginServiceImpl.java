package org.luckycloud.security.components.loginProcess.impl;

import jakarta.annotation.Resource;
import org.luckycloud.domain.user.CloudUserInfoDO;
import org.luckycloud.enums.LoginType;
import org.luckycloud.mapper.user.CloudUserInfoMapper;
import org.luckycloud.security.components.loginProcess.LoginTypeService;
import org.luckycloud.security.dto.LoginRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MailPasswordLoginServiceImpl implements LoginTypeService {

    @Resource
    CloudUserInfoMapper cloudUserInfoMapper;

    @Override
    public LoginType getLoginType() {
        return LoginType.MAIL_PASSWORD;
    }

    @Override
    public Authentication loginProcess(LoginRequest userInfo) {
        String mail = userInfo.getMail();
        CloudUserInfoDO userInfoDO = cloudUserInfoMapper.findByMail(mail);
        if (userInfoDO == null) {
            throw new UsernameNotFoundException("邮箱未注册");
        }
        userInfo.setUserId(userInfoDO.getUserId());
//        String privateKey = RSAUtils.getPrivateKey();
//        String password = RSAUtils.decrypt(userInfo.getPassword(),privateKey);
        return new UsernamePasswordAuthenticationToken(userInfo.getUserId(), userInfo.getPassword());

    }


}
