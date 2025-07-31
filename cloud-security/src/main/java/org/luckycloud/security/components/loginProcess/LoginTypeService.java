package org.luckycloud.security.components.loginProcess;


import org.luckycloud.enums.LoginType;
import org.luckycloud.security.dto.LoginRequest;
import org.springframework.security.core.Authentication;

public interface LoginTypeService {

    LoginType getLoginType();
    Authentication loginProcess(LoginRequest userInfo);

}
