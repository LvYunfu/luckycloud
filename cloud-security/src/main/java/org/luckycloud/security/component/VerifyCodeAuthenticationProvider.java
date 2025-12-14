package org.luckycloud.security.component;

import lombok.Getter;
import org.luckycloud.security.dto.LoginRequest;
import org.luckycloud.security.service.AuthCommonService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author lvyf
 * @description:
 * @date 2024/7/21
 */

@Getter
public class VerifyCodeAuthenticationProvider implements AuthenticationProvider {


    private AuthCommonService authCommonService;


    private UserDetailsService userDetailsService;


    public void setAuthCommonService(AuthCommonService authCommonService) {
        this.authCommonService = authCommonService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (!(authentication instanceof VerifyCodeAuthenticationToken)) {
            throw new IllegalArgumentException("Only VerifyCodeAuthenticationToken is supported");
        }
        LoginRequest loginInfoDTO = (LoginRequest) authentication.getCredentials();
        authCommonService.checkVerifyCode(loginInfoDTO.getVerifyId(), loginInfoDTO.getVerifyCode());
        UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(loginInfoDTO.getUserId());

        return VerifyCodeAuthenticationToken.authenticated(loadedUser);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (VerifyCodeAuthenticationToken.class.isAssignableFrom(authentication));

    }
}
