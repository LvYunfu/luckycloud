package org.luckycloud.security.components.loginProcess;

import org.luckycloud.dto.common.ResponseCode;
import org.luckycloud.enums.LoginType;
import org.luckycloud.exception.BusinessException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class LoginTypeFactory implements InitializingBean, ApplicationContextAware {

    private static final Map<LoginType, LoginTypeService> LOGIN_TYPE_PROCESS = new HashMap<>();

    private ApplicationContext appContext;


    public  static  LoginTypeService getLoginWayProcess(LoginType loginWay) {
        if (loginWay == null) {
            throw new BusinessException(ResponseCode.OPERATE_FAILED.getCode(), "不支持的登录方式");
        }
        return LOGIN_TYPE_PROCESS.get(loginWay);

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        appContext.getBeansOfType(LoginTypeService.class)
                .values()
                .forEach(loginTypeService -> LOGIN_TYPE_PROCESS.put(loginTypeService.getLoginType(), loginTypeService));
    }
}
