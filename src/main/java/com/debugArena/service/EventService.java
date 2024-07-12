package com.debugArena.service;

import com.debugArena.model.dto.view.EventShortInfoViewModel;

import java.util.List;

public interface EventService {

    List<EventShortInfoViewModel> getEvents();
}
