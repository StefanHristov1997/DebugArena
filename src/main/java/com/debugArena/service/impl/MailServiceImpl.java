package com.debugArena.service.impl;

import com.debugArena.model.dto.binding.EmailSenderBindingModel;
import com.debugArena.model.dto.binding.UserEmailBindingModel;
import com.debugArena.model.dto.view.DailyNotificationProblemViewModel;
import com.debugArena.model.dto.view.EventDetailsInfoViewModel;
import com.debugArena.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    private final String debugArenaContactEmail;
    private final String debugArenaMainEmail;

    @Autowired
    public MailServiceImpl(
            TemplateEngine templateEngine,
            JavaMailSender mailSender,
            @Value("${mail.receiver}") String debugArenaContactEmail,
            @Value("${mail.sender}") String debugArenaMainEmail) {
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
        this.debugArenaContactEmail = debugArenaContactEmail;
        this.debugArenaMainEmail = debugArenaMainEmail;
    }

    @Override
    public void sendToOurEmail(EmailSenderBindingModel emailSenderBindingModel) {

        final MimeMessage mimeMessage = mailSender.createMimeMessage();

        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        try {
            messageHelper.setTo(debugArenaContactEmail);
            messageHelper.setFrom(emailSenderBindingModel.getEmail());
            messageHelper.setReplyTo(emailSenderBindingModel.getEmail());
            messageHelper.setSubject("User question");
            messageHelper.setText(emailSenderBindingModel.getMessage());

            mailSender.send(messageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendDailyProblemsNotifications(
            List<DailyNotificationProblemViewModel> dailyNotificationProblems,
            List<UserEmailBindingModel> userEmailBindingModels) {

        final MimeMessage mimeMessage = mailSender.createMimeMessage();

        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        userEmailBindingModels.forEach(userEmail -> {
            try {
                messageHelper.setTo(userEmail.getEmail());
                messageHelper.setFrom(debugArenaMainEmail);
                messageHelper.setReplyTo(debugArenaMainEmail);
                messageHelper.setSubject("Daily problems notification from DebugArena");
                messageHelper.setText(generateDailyProblemsNotificationEmailBody(dailyNotificationProblems), true);
                mailSender.send(messageHelper.getMimeMessage());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private String generateDailyProblemsNotificationEmailBody(List<DailyNotificationProblemViewModel> dailyNotificationProblems) {

        Context context = new Context();

        context.setVariable("dailyProblems", dailyNotificationProblems);

        return templateEngine.process("/email/daily-problems-notification", context);
    }

    @Override
    public void sendWeeklyEventsNotifications(
            List<EventDetailsInfoViewModel> eventDetailsInfoViewModels,
            List<UserEmailBindingModel> userEmailBindingModels) {

        final MimeMessage mimeMessage = mailSender.createMimeMessage();

        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        userEmailBindingModels.forEach(userEmail -> {
            try {
                messageHelper.setTo(userEmail.getEmail());
                messageHelper.setFrom(debugArenaMainEmail);
                messageHelper.setReplyTo(debugArenaMainEmail);
                messageHelper.setSubject("Weekly events notification from DebugArena");
                messageHelper.setText(generateWeeklyEventsNotificationEmailBody((eventDetailsInfoViewModels)), true);
                mailSender.send(messageHelper.getMimeMessage());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private String generateWeeklyEventsNotificationEmailBody(List<EventDetailsInfoViewModel> eventDetailsInfoViewModels) {

        Context context = new Context();

        context.setVariable("weeklyEvents", eventDetailsInfoViewModels);

        return templateEngine.process("/email/weekly-events-notification", context);
    }
}
