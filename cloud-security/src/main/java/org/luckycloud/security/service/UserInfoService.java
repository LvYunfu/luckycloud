package org.luckycloud.security.service;

import jakarta.annotation.Resource;
import org.luckycloud.domain.user.CloudUserInfoDO;
import org.luckycloud.exception.BusinessException;
import org.luckycloud.mapper.user.CloudUserInfoMapper;
import org.luckycloud.security.dto.RegisterRequest;
import org.luckycloud.security.dto.UpdatePasswordRequest;
import org.luckycloud.security.dto.UserInfoResponse;
import org.luckycloud.security.entity.SysUserDetail;
import org.luckycloud.security.util.UserUtils;
import org.luckycloud.utils.UploadUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.luckycloud.dto.common.ResponseCode.SUCCESS;

/**
 * @author lvyf
 * @description:
 * @date 2025/12/2
 */
@Service

public class UserInfoService {

    private final CloudUserInfoMapper userMapper;

    private final UploadUtils uploadUtils;

    @Resource
    private PasswordEncoder passwordEncoder;

    public UserInfoService(CloudUserInfoMapper userMapper, UploadUtils uploadUtils) {
        this.userMapper = userMapper;
        this.uploadUtils = uploadUtils;
    }


    public UserInfoResponse getUserInfo() {
        SysUserDetail sysUserDetail = UserUtils.getUserBaseInfo();
        UserInfoResponse response = new UserInfoResponse();
        response.setUserId(sysUserDetail.getUserId());
        response.setUserName(sysUserDetail.getUsername());
        CloudUserInfoDO cloudUserInfoDO = userMapper.findByUserId(sysUserDetail.getUserId());
        response.setMail(cloudUserInfoDO.getMail());
        response.setProfile(cloudUserInfoDO.getProfile());
        response.setAvatar(uploadUtils.getFileUrl(sysUserDetail.getUserId(), cloudUserInfoDO.getAvatar()));
        return response;
    }

    public void updateUserInfo(RegisterRequest request) {
        // 检查用户名是否已存在
        CloudUserInfoDO cloudUserInfoDO = userMapper.findByUsername(request.getUserName());
        String userId = UserUtils.getUserId();
        if (cloudUserInfoDO != null && !cloudUserInfoDO.getUserId().equals(userId)) {
            throw new BusinessException(SUCCESS, "用户名已存在");
        }
        // 更新
        CloudUserInfoDO user = new CloudUserInfoDO();
        user.setUserId(userId);
        user.setUserName(request.getUserName());
        user.setProfile(request.getProfile());
        user.setAvatar(request.getAvatar());
        userMapper.updateByPrimaryKeySelective(user);
    }


    public void updatePassword(UpdatePasswordRequest request) {
        String userId = UserUtils.getUserId();
        CloudUserInfoDO cloudUserInfoDO = userMapper.findByUserId(userId);
        if (!passwordEncoder.matches(request.getOldPassword(), cloudUserInfoDO.getPassword())) {
            throw new BusinessException(SUCCESS, "旧密码错误");
        }

        // 更新
        CloudUserInfoDO user = new CloudUserInfoDO();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userMapper.updateByPrimaryKeySelective(user);
    }
}
