package com.debugArena.schedulling;

import com.debugArena.model.dto.binding.UserEmailBindingModel;
import com.debugArena.model.dto.view.DailyNotificationProblemViewModel;
import com.debugArena.service.MailService;
import com.debugArena.service.ProblemService;
import com.debugArena.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DailyNotificationScheduleTask {

    private final MailService emailService;
    private final ProblemService problemService;
    private final UserService userService;
    @Autowired
    public DailyNotificationScheduleTask(MailService emailService, ProblemService problemService, UserService userService) {
        this.emailService = emailService;
        this.problemService = problemService;
        this.userService = userService;
    }

    @Scheduled(cron = "1 * * * * * ")
    public void sendDailyNotifications() {

        List<DailyNotificationProblemViewModel> dailyNotificationProblems = problemService.getDailyNotificationProblems();
        List<UserEmailBindingModel> userEmails = userService.getUserEmails();
        emailService.sendDailyNotifications(dailyNotificationProblems, userEmails);
    }
}
