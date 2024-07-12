package com.debugArena.service;

import com.debugArena.model.dto.binding.AddEventBindingModel;
import com.debugArena.model.dto.view.EventShortInfoViewModel;

import java.util.List;

public interface EventService {

    void registerEvent(AddEventBindingModel addEventBindingModel);

    List<EventShortInfoViewModel> getEvents();
}
