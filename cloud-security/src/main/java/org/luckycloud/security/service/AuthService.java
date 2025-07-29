package org.luckycloud.security.service;

import org.luckycloud.domain.user.CloudUserInfoDO;
import org.luckycloud.mapper.user.CloudUserInfoMapper;
import org.luckycloud.security.dto.RegisterRequest;
import org.luckycloud.security.util.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

import static org.luckycloud.constant.SystemConstant.ENABLE;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CloudUserInfoMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager,
                      JwtTokenUtil jwtTokenUtil,
                      CloudUserInfoMapper userMapper,
                      PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Transactional
    public void register(RegisterRequest request) {
        // 检查用户名是否已存在
        if (userMapper.findByUsername(request.getUserName()) != null) {
            throw new RuntimeException("Username already exists");
        }

        // 创建新用户
        CloudUserInfoDO user = new CloudUserInfoDO();
        user.setUserId(UUID.randomUUID().toString());
        user.setUserName(request.getUserName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setMail(request.getMail());
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setAccountStatus(ENABLE); // 正常状态
        user.setStatus(ENABLE); // 生效状态

        // 保存用户
        userMapper.insert(user);
    }
}
