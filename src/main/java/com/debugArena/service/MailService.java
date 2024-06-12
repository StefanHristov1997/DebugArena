package com.debugArena.service;

import com.debugArena.model.events.UserContactedUsEvent;

public interface MailService {

    public void receiveEmail(UserContactedUsEvent event);
}
