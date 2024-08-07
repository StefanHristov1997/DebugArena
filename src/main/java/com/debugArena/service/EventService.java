package com.debugArena.service;

import com.debugArena.model.dto.binding.AddEventBindingModel;
import com.debugArena.model.dto.view.EventDetailsInfoViewModel;
import com.debugArena.model.dto.view.EventShortInfoViewModel;

import java.util.List;

public interface EventService {

    void registerEvent(AddEventBindingModel addEventBindingModel);

    EventDetailsInfoViewModel getEventDetailsInfoById(Long eventId);

    void deleteEvent(Long eventId);

    List<EventShortInfoViewModel> getEvents();

    List<EventDetailsInfoViewModel> getWeeklyEvents();
}
