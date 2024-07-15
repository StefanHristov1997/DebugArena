package com.debugArena.service;

import com.debugArena.model.dto.binding.EmailSenderBindingModel;
import com.debugArena.model.dto.binding.UserEmailBindingModel;
import com.debugArena.model.dto.view.DailyNotificationProblemViewModel;
import com.debugArena.model.dto.view.EventDetailsInfoViewModel;

import java.util.List;

public interface MailService {

    void sendToOurEmail(EmailSenderBindingModel emailSenderBindingModel);

    void sendDailyProblemsNotifications(
            List<DailyNotificationProblemViewModel> dailyNotificationProblems,
            List<UserEmailBindingModel> userEmailBindingModels);

    void sendWeeklyEventsNotifications(
            List<EventDetailsInfoViewModel> eventDetailsInfoViewModels,
            List<UserEmailBindingModel> userEmailBindingModels);
}



