package org.luckycloud.security.controller;

import org.luckycloud.dto.common.Response;
import org.luckycloud.dto.secruity.SysUserToken;
import org.luckycloud.security.dto.*;
import org.luckycloud.security.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Response<SysUserToken> login(@RequestBody LoginRequest loginRequest) {

        String token = authService.login(loginRequest);
        SysUserToken sysUserToken = new SysUserToken();
        sysUserToken.setToken(token);
        return Response.successData(sysUserToken,"登录成功");
    }

    @PostMapping("/register")
    public Response<Void> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return Response.success("注册成功");
    }

    @PostMapping("/forget-password")
    public Response<Void> forgetPassword(@RequestBody ForgetPasswordRequest request) {
        authService.forgetPassword(request);
        return Response.success("密码修改成功");

    }

    @PostMapping("/send-code")
    public Response<String> sendCode(@RequestBody SendCodeRequest request) {
        return Response.successData(authService.sendCode(request));

    }

}
