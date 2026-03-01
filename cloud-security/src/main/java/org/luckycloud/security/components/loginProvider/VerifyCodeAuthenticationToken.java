package org.luckycloud.security.components.loginProvider;

import org.luckycloud.security.dto.LoginRequest;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

/**
 * @author lvyf
 * @description:
 * @date 2024/7/20
 */

public class VerifyCodeAuthenticationToken extends AbstractAuthenticationToken {

    private  Object principal;

    private LoginRequest credentials;

    public VerifyCodeAuthenticationToken() {
        super(new ArrayList<>());
    }

    public VerifyCodeAuthenticationToken(LoginRequest sysUserDetail) {
        super(new ArrayList<>());
        this.credentials = sysUserDetail;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public  static  VerifyCodeAuthenticationToken authenticated(UserDetails userInfoDO){
        VerifyCodeAuthenticationToken verifyCodeAuthenticationToken = new VerifyCodeAuthenticationToken();
        verifyCodeAuthenticationToken.principal = userInfoDO;
        verifyCodeAuthenticationToken.setAuthenticated(true);
        return verifyCodeAuthenticationToken;

    }


}
