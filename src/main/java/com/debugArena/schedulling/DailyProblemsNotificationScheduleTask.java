package com.debugArena.schedulling;

import com.debugArena.model.dto.binding.UserEmailBindingModel;
import com.debugArena.model.dto.view.DailyNotificationProblemViewModel;
import com.debugArena.service.MailService;
import com.debugArena.service.ProblemService;
import com.debugArena.service.UserService;
import com.debugArena.service.helpers.SmtpServerStatusHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DailyProblemsNotificationScheduleTask {

    private final MailService mailService;
    private final SmtpServerStatusHelper smtpServerStatusHelper;
    private final ProblemService problemService;
    private final UserService userService;

    @Autowired
    public DailyProblemsNotificationScheduleTask(MailService mailService, SmtpServerStatusHelper smtpServerStatusHelper, ProblemService problemService, UserService userService) {
        this.mailService = mailService;
        this.smtpServerStatusHelper = smtpServerStatusHelper;
        this.problemService = problemService;
        this.userService = userService;
    }

    //0 0 22 * * 1-7
    //30 * * * * ?
    @Scheduled(cron = "0 0 22 * * 1-7")
    public void sendDailyProblemsNotifications() {
        if(smtpServerStatusHelper.isSmtpServerUp()){
            List<DailyNotificationProblemViewModel> dailyNotificationProblems = problemService.getDailyNotificationProblems();
            List<UserEmailBindingModel> userEmails = userService.getUserEmails();

            mailService.sendDailyProblemsNotifications(dailyNotificationProblems, userEmails);
        }
    }
}
