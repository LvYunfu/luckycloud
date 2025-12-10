package org.luckycloud.security.util;


import jakarta.annotation.Resource;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.luckycloud.dto.common.ResponseCode;
import org.luckycloud.exception.BusinessException;
import org.luckycloud.dto.common.Mail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
public class SendMailUtils {
    @Value("${spring.mail.username}")
    private String sendMail;
    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.sender-name}")
    private String sendName;
    public void sendMail(Mail mail) {

        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
            helper.setFrom(sendMail, sendName);
            //邮件收件人
            helper.setTo(mail.getReceiveMail().split(","));
            //邮件主题
            helper.setSubject(mail.getSubject());
            //邮件内容
            helper.setText(mail.getSendMessage());
            //邮件发送时间
            helper.setSentDate(new Date());
            javaMailSender.send(mimeMessage);
            log.info("成功发送邮件");
        } catch (Exception e) {
            log.info("邮件发送失败", e);
            throw new BusinessException(ResponseCode.OPERATE_FAILED, "邮件发送失败，请稍后重试");
        }

    }
}

