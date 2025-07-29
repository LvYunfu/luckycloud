package org.luckycloud.security.controller;

import org.luckycloud.dto.common.Response;
import org.luckycloud.dto.secruity.SysUserToken;
import org.luckycloud.security.dto.LoginRequest;
import org.luckycloud.security.dto.RegisterRequest;
import org.luckycloud.security.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Response<SysUserToken> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUserName();
        String password = loginRequest.getPassword();

        String token = authService.login(username, password);
        SysUserToken sysUserToken = new SysUserToken();
        sysUserToken.setToken(token);
        return Response.success("登录成功",sysUserToken);
    }

    @PostMapping("/register")
    public Response<Void> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return Response.success("注册成功");

    }
}
