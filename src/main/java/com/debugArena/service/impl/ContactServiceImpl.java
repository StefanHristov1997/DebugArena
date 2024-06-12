package com.debugArena.service.impl;

import com.debugArena.model.events.UserContactedUsEvent;
import com.debugArena.service.ContactService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    @Override
    @EventListener(UserContactedUsEvent.class)
    public void receiveEmail(UserContactedUsEvent event) {

        System.out.println("User with email: " + event.getUserEmail());
    }
}
