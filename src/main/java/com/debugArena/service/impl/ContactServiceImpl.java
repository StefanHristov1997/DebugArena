package com.debugArena.service.impl;

import com.debugArena.events.UserContactedUsEvent;
import com.debugArena.exeption.ServerConnectionException;
import com.debugArena.service.ContactService;
import com.debugArena.service.MailService;
import com.debugArena.service.helpers.SmtpServerStatusHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    private final MailService mailService;
    private final SmtpServerStatusHelper smtpServerStatusHelper;

    @Autowired
    public ContactServiceImpl(MailService mailService, SmtpServerStatusHelper smtpServerStatusHelper) {
        this.mailService = mailService;
        this.smtpServerStatusHelper = smtpServerStatusHelper;
    }

    @Override
    @EventListener(UserContactedUsEvent.class)
    public void receiveUserEmail(UserContactedUsEvent event) {

        if (!smtpServerStatusHelper.isSmtpServerUp()) {
            throw new ServerConnectionException();
        }

        mailService.sendToOurEmail(event.getEmailSenderBindingModel());
    }
}
