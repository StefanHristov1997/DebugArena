package com.debugArena.service.impl;

import com.debugArena.model.dto.binding.EmailSenderBindingModel;
import com.debugArena.service.MailService;
import com.debugArena.service.helpers.LoggedUserHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final String emailReceiver;
    private final LoggedUserHelper loggedUserHelper;

    @Autowired
    public MailServiceImpl(
            JavaMailSender mailSender,
            @Value("${mail.receiver}") String emailReceiver,
            LoggedUserHelper loggedUserHelper) {
        this.mailSender = mailSender;
        this.emailReceiver = emailReceiver;
        this.loggedUserHelper = loggedUserHelper;
    }

    @Override
    public void sendEmail(EmailSenderBindingModel emailSenderBindingModel) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        try {
            messageHelper.setTo(emailReceiver);
            messageHelper.setFrom(emailSenderBindingModel.getEmail());
            messageHelper.setReplyTo(emailSenderBindingModel.getEmail());
            messageHelper.setSubject("User question");
            messageHelper.setText(emailSenderBindingModel.getMessage());

           mailSender.send(messageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
