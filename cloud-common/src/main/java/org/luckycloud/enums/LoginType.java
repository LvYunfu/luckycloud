package org.luckycloud.enums;

/**
 * @author lvyf
 * @description:
 * @date 2025/7/31
 */
public enum LoginType  {

    MAIL_PASSWORD("MP", "邮箱密码"),

    MAIL_VERIFICATION_CODE("MC", "邮箱验证码"),

    WE_CHAT_SCAN_CODE("WC", "微信扫码"),

    ACCOUNT_PASSWORD("AP", "账户密码");

    private final String key;
    private final String name;

    LoginType(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public static LoginType getLoginType(String loginType) {
        for (LoginType day : values()) {
            if (day.key.equals(loginType)) {
                return day;
            }
        }
        return null;
    }

}
