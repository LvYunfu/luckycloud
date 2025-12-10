package org.luckycloud.security.util;

import lombok.extern.log4j.Log4j2;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author lvyf
 * @description:
 * @date 2023/7/3 23:22
 */
@Log4j2
public class VerifyCodeUtils {
    private VerifyCodeUtils() {
    }

    public static String generateVerificationCode(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        // 生成指定长度的随机数字验证码
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // 生成0-9之间的随机数字
            sb.append(digit);
        }

        return sb.toString();
    }
}
