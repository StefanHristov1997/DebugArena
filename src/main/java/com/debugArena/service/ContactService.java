package com.debugArena.service;

import com.debugArena.events.UserContactedUsEvent;

public interface ContactService {

    void receiveUserEmail(UserContactedUsEvent event);
}
