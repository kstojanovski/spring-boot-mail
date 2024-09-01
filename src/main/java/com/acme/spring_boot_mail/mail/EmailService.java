package com.acme.spring_boot_mail.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    @Value("${notification.host_name}")
    String hostName;

    @Value("${notification.host_port}")
    int hostPort;

    public void sendMail() throws MessagingException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setHost(hostName);
        mailSender.setPort(hostPort);
        mailSender.setUsername("");
        mailSender.setPassword("");

        properties(mailSender);

        mailSender.send(getMimeMessage(mailSender));
    }

    private static void properties(JavaMailSenderImpl mailSender) {
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
    }

    private static MimeMessage getMimeMessage(JavaMailSenderImpl mailSender) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("spring-boot-mail@acme.com");
        mimeMessageHelper.setTo("mailhog@acme.com");
        mimeMessageHelper.setSubject("Sending fancy mails");
        mimeMessageHelper.setText("Hello All!!!\n\nMy mail is beautiful!\n\nBest Regards,\nAcme");
        return mimeMessage;
    }
}
