package org.luckycloud.security.service;

import org.luckycloud.domain.user.CloudUserInfoDO;
import org.luckycloud.enums.LoginType;
import org.luckycloud.exception.BusinessException;
import org.luckycloud.mapper.user.CloudUserInfoMapper;
import org.luckycloud.security.components.loginProcess.LoginTypeFactory;
import org.luckycloud.security.components.loginProcess.LoginTypeService;
import org.luckycloud.security.dto.LoginRequest;
import org.luckycloud.security.dto.RegisterRequest;
import org.luckycloud.security.dto.UserInfoResponse;
import org.luckycloud.security.entity.SysUserDetail;
import org.luckycloud.security.util.JwtTokenUtil;
import org.luckycloud.security.util.UserUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.luckycloud.constant.SystemConstant.ENABLE;
import static org.luckycloud.dto.common.ResponseCode.OPERATE_FAILED;
import static org.luckycloud.dto.common.ResponseCode.SUCCESS;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CloudUserInfoMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenUtil jwtTokenUtil,
                       CloudUserInfoMapper userMapper,
                       PasswordEncoder passwordEncoder

    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;

    }

    public String login(LoginRequest request) {
        try {
            LoginTypeService loginTypeService = LoginTypeFactory.getLoginWayProcess(LoginType.getLoginType(request.getLoginType()));
            Authentication authenticationToken = loginTypeService.loginProcess(request);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SysUserDetail userDetails = (SysUserDetail) authentication.getPrincipal();
            return jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            throw new BusinessException(OPERATE_FAILED, "登录失败，用户名或密码错误");
        }
    }

    @Transactional
    public void register(RegisterRequest request) {
        // 检查用户名是否已存在
        if (userMapper.findByUsername(request.getUserName()) != null) {
            throw new BusinessException(SUCCESS, "用户名已存在");
        }

        // 创建新用户
        CloudUserInfoDO user = new CloudUserInfoDO();
        user.setUserId(UUID.randomUUID().toString());
        user.setUserName(request.getUserName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setMail(request.getMail());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setAccountStatus(ENABLE); // 正常状态
        user.setStatus(ENABLE); // 生效状态

        // 保存用户
        userMapper.insert(user);
    }


}
