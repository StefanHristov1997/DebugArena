package com.debugArena.service.impl;

import com.debugArena.model.events.UserContactedUsEvent;
import com.debugArena.service.MailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Override
    @EventListener(UserContactedUsEvent.class)
    public void receiveEmail(UserContactedUsEvent event) {

    }
}
