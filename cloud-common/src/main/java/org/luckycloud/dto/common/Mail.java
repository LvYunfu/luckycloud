package org.luckycloud.dto.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LvYunfu
 * @description: 邮箱信息
 * @date 2022/6/29 16:38
 */

@Data
public class Mail implements Serializable {

    /**
     * 发送邮箱
     */

    private  String sendMail;
    /**
     * 接收邮箱（多个邮箱则用逗号","隔开）
     */
    private String receiveMail;
    /**
     * 邮件内容
     */
    private String sendMessage;
    /**
    * 邮件主题
    */
    private String subject;

    /**
    * 发送日期
    */
    private String sentDate;
    /**
    * 抄送邮箱
    */
    private String copyMail;

}
