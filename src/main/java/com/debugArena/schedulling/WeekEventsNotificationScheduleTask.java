package com.debugArena.schedulling;

import com.debugArena.model.dto.binding.UserEmailBindingModel;
import com.debugArena.model.dto.view.EventDetailsInfoViewModel;
import com.debugArena.service.EventService;
import com.debugArena.service.MailService;
import com.debugArena.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class WeekEventsNotificationScheduleTask {

    private final MailService emailService;
    private final EventService eventService;
    private final UserService userService;


    @Autowired
    public WeekEventsNotificationScheduleTask(
            MailService emailService,
            EventService eventService,
            UserService userService) {

        this.emailService = emailService;
        this.eventService = eventService;
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 * * 0 ?")
    public void sendWeeklyEventsNotification() {
        List<EventDetailsInfoViewModel> weeklyEvents = eventService.getWeeklyEvents();
        List<UserEmailBindingModel> userEmails = userService.getUserEmails();

        emailService.sendWeeklyEventsNotifications(weeklyEvents, userEmails);
    }
}
