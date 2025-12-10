package org.luckycloud.constant;

import java.util.HashMap;
import java.util.Map;

import static org.luckycloud.constant.SystemConstant.VerifyType.*;


/**
 * @author lvyf
 * @description:
 * @date 2023/7/3 23:08
 */
public class MailTemplate {
    private MailTemplate() {
    }

    private static final Map<String, String> MAIL_TEAMPLATE_MAP = new HashMap<>();
    public static final String REGISTER_VERIFY = "欢迎您注册零雨笔记，此次注册验证码为%s，请勿泄露！";
    public static final String FORGET_PASSWORD_VERIFY = "您正在找回零雨笔记密码，验证码为%s，请勿泄露！";

    static {
        MAIL_TEAMPLATE_MAP.put(REGISTER_VERIFY_CODE, REGISTER_VERIFY);
        MAIL_TEAMPLATE_MAP.put(FORGET_PASSWORD_VERIFY_CODE, FORGET_PASSWORD_VERIFY);

    }
    public static String getTemplate(String type) {
        return MAIL_TEAMPLATE_MAP.get(type);
    }

}
