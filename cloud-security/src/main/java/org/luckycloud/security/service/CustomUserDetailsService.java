package org.luckycloud.security.service;

import jakarta.annotation.Resource;
import org.luckycloud.domain.user.CloudUserInfoDO;
import org.luckycloud.mapper.user.CloudUserInfoMapper;
import org.luckycloud.security.entity.SysUserDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CloudUserInfoMapper userMapper;

    @Resource
    PasswordEncoder passwordEncoder;
    public CustomUserDetailsService(CloudUserInfoMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CloudUserInfoDO user = userMapper.findByUserId(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return SysUserDetail.convertToSysUser(user);
    }
}
