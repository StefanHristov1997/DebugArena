package com.debugArena.service.impl;

import com.debugArena.model.events.UserContactedUsEvent;
import com.debugArena.service.ContactService;
import com.debugArena.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    private final MailService mailService;

    @Autowired
    public ContactServiceImpl(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    @EventListener(UserContactedUsEvent.class)
    public void receiveUserEmail(UserContactedUsEvent event) {
        mailService.sendEmail(event.getEmailSenderBindingModel());
    }
}
