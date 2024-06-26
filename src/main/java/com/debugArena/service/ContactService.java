package com.debugArena.service;

import com.debugArena.model.events.UserContactedUsEvent;

public interface ContactService {

    void userContactedUs(UserContactedUsEvent event);
}
