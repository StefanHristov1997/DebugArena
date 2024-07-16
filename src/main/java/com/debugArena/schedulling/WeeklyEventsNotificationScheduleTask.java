package com.debugArena.schedulling;

import com.debugArena.model.dto.binding.UserEmailBindingModel;
import com.debugArena.model.dto.view.EventDetailsInfoViewModel;
import com.debugArena.service.EventService;
import com.debugArena.service.MailService;
import com.debugArena.service.UserService;
import com.debugArena.service.helpers.SmtpServerStatusHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeeklyEventsNotificationScheduleTask {

    private final MailService emailService;
    private final SmtpServerStatusHelper smtpServerStatusHelper;
    private final EventService eventService;
    private final UserService userService;


    @Autowired
    public WeeklyEventsNotificationScheduleTask(
            MailService emailService, SmtpServerStatusHelper smtpServerStatusHelper,
            EventService eventService,
            UserService userService) {

        this.emailService = emailService;
        this.smtpServerStatusHelper = smtpServerStatusHelper;
        this.eventService = eventService;
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 * * 7 ?")
    public void sendWeeklyEventsNotification() {
        if(smtpServerStatusHelper.isSmtpServerUp()){
            List<EventDetailsInfoViewModel> weeklyEvents = eventService.getWeeklyEvents();
            List<UserEmailBindingModel> userEmails = userService.getUserEmails();

            emailService.sendWeeklyEventsNotifications(weeklyEvents, userEmails);
        }
    }
}
