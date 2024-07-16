package com.debugArena.service.helpers;

import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class SmtpServerStatusHelper {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private  String password;

    public boolean isSmtpServerUp() {
        try {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(host);
            mailSender.setPort(port);
            mailSender.setUsername(username);
            mailSender.setPassword(password);

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.starttls.enable", true);

            MimeMessage message = mailSender.createMimeMessage();
            Transport transport = mailSender.getSession().getTransport("smtp");
            transport.connect(host, port, username, password);
            transport.close();

            return true;
        } catch (MessagingException e) {
            return false;
        }
    }
}
