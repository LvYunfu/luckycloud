package org.luckycloud.security.service;

import org.luckycloud.constant.MailTemplate;
import org.luckycloud.domain.common.CloudVerifyCodeInfoDO;
import org.luckycloud.domain.user.CloudUserInfoDO;
import org.luckycloud.dto.common.Mail;
import org.luckycloud.enums.LoginType;
import org.luckycloud.exception.BusinessException;
import org.luckycloud.mapper.common.CloudVerifyCodeInfoMapper;
import org.luckycloud.mapper.user.CloudUserInfoMapper;
import org.luckycloud.security.components.loginProcess.LoginTypeFactory;
import org.luckycloud.security.components.loginProcess.LoginTypeService;
import org.luckycloud.security.dto.*;
import org.luckycloud.security.entity.SysUserDetail;
import org.luckycloud.security.util.JwtTokenUtil;
import org.luckycloud.security.util.RequestUtils;
import org.luckycloud.security.util.SendMailUtils;
import org.luckycloud.security.util.VerifyCodeUtils;
import org.luckycloud.utils.GenerateIdUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static org.luckycloud.constant.SystemConstant.DISABLE;
import static org.luckycloud.constant.SystemConstant.ENABLE;
import static org.luckycloud.dto.common.ResponseCode.OPERATE_FAILED;
import static org.luckycloud.dto.common.ResponseCode.SUCCESS;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CloudUserInfoMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final SendMailUtils sendMailUtils;

    private final CloudVerifyCodeInfoMapper verifyCodeInfoMapper;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenUtil jwtTokenUtil,
                       CloudUserInfoMapper userMapper,
                       PasswordEncoder passwordEncoder,
                       SendMailUtils sendMailUtils,
                       CloudVerifyCodeInfoMapper verifyCodeInfoMapper

    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.sendMailUtils = sendMailUtils;
        this.verifyCodeInfoMapper = verifyCodeInfoMapper;

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
        checkVerifyCode(request.getVerifyId(), request.getVerifyCode());
        // 检查用户名是否已存在
        if (userMapper.findByUsername(request.getUserName()) != null) {
            throw new BusinessException(SUCCESS, "用户名已存在");
        }
        // 创建新用户
        CloudUserInfoDO user = new CloudUserInfoDO();
        user.setUserId(GenerateIdUtils.generateId());
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


    public String sendCode(SendCodeRequest request) {
        //防止重复发送验证码
        String mail = request.getMail();
        String type = request.getType();
        String ip = RequestUtils.getClientIp();
        CloudVerifyCodeInfoDO verifyCodeInfo = verifyCodeInfoMapper.getVerifyCodeInfo(mail, ip, type);

        if (!Objects.isNull(verifyCodeInfo) && verifyCodeInfo.getSendTime().plusSeconds(60).isAfter(LocalDateTime.now())) {
            throw new BusinessException(OPERATE_FAILED, "验证码发送频繁，请稍后再试");
        }
        if (!Objects.isNull(verifyCodeInfo)) {
            CloudVerifyCodeInfoDO updateDo = new CloudVerifyCodeInfoDO();
            updateDo.setVerifyId(verifyCodeInfo.getVerifyId());
            updateDo.setStatus(DISABLE);
            verifyCodeInfoMapper.updateByPrimaryKeySelective(updateDo);
        }
        String verifyCode = VerifyCodeUtils.generateVerificationCode(6);
        CloudVerifyCodeInfoDO verifyCodeInfoDO = new CloudVerifyCodeInfoDO();
        verifyCodeInfoDO.setVerifyId(GenerateIdUtils.generateId());
        verifyCodeInfoDO.setUserId(mail);
        verifyCodeInfoDO.setExpireTime(90);
        verifyCodeInfoDO.setVerifyCode(verifyCode);
        verifyCodeInfoDO.setVerifyType(type);
        verifyCodeInfoDO.setStatus(ENABLE);
        verifyCodeInfoDO.setUserIp(ip);
        verifyCodeInfoMapper.insert(verifyCodeInfoDO);
        Mail mailContent = new Mail();
        String mailTemplate = MailTemplate.getTemplate(type);
        String message = String.format(mailTemplate, verifyCode);
        mailContent.setReceiveMail(mail);
        mailContent.setSendMessage(message);
        mailContent.setSubject("验证码");
        sendMailUtils.sendMail(mailContent);
        return verifyCodeInfoDO.getVerifyId();
    }

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
