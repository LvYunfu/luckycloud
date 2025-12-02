package org.luckycloud.security.controller;

import jakarta.annotation.Resource;
import org.luckycloud.dto.common.Response;
import org.luckycloud.security.dto.RegisterRequest;
import org.luckycloud.security.dto.UserInfoResponse;
import org.luckycloud.security.service.AuthService;
import org.luckycloud.security.service.UserInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lvyf
 * @description:
 * @date 2025/12/2
 */
@RestController
@RequestMapping("/auth-user")
public class UserController {


    private final UserInfoService userInfoService;

    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }


    @PostMapping("/user-info")
    public Response<UserInfoResponse> getUserInfo() {

        return Response.successData(userInfoService.getUserInfo());

    }
    @PostMapping("/update-user")
    public Response<Void> updateUserInfo(@RequestBody RegisterRequest request) {
        userInfoService.updateUserInfo(request);
        return Response.success("更新成功");

    }
}
