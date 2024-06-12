package com.debugArena.service;

import com.debugArena.model.events.UserContactedUsEvent;

public interface ContactService {

    public void receiveEmail(UserContactedUsEvent event);
}
