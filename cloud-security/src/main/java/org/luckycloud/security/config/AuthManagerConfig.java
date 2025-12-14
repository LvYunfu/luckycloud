package org.luckycloud.security.config;


import org.luckycloud.security.components.loginProvider.VerifyCodeAuthenticationProvider;
import org.luckycloud.security.service.AuthCommonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * @author lvyf
 * @description: 过滤器
 * @date 2023/4/6 23:24
 */
@Configuration
public class AuthManagerConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * @description: 密码登录验证
     * @param:
     * @return:
     * @author lvyf
     * @date: 2023/10/18
     */
    @Bean(name = "authenticationProvider")
    AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
    /**
     * @description: 微信登录验证
     * @param:
     * @return:
     * @author lvyf
     * @date: 2023/10/18
     */
    @Bean(name = "verifyCodeAuthenticationProvider")
    AuthenticationProvider verifyCodeAuthenticationProvider(UserDetailsService userDetailsService, AuthCommonService authCommonService) {
        VerifyCodeAuthenticationProvider provider = new VerifyCodeAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setAuthCommonService(authCommonService);
        return provider;
    }


    @Bean(name = "authenticationManager")
    @Primary
    AuthenticationManager authenticationManager(List<AuthenticationProvider> authenticationProvider) {
        return new ProviderManager(authenticationProvider);
    }


}
