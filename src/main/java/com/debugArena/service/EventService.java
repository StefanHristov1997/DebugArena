package com.debugArena.service;

import com.debugArena.model.dto.binding.AddEventBindingModel;
import com.debugArena.model.dto.view.EventDetailsInfoViewModel;
import com.debugArena.model.dto.view.EventShortInfoViewModel;

import java.util.List;

public interface EventService {

    void registerEvent(AddEventBindingModel addEventBindingModel);

    EventDetailsInfoViewModel getEventDetailsInfoById(Long id);

    List<EventShortInfoViewModel> getEvents();
}
