package com.debugArena.service.impl;

import com.debugArena.events.UserContactedUsEvent;
import com.debugArena.exeption.EmailConnectionException;
import com.debugArena.service.ContactService;
import com.debugArena.service.MailService;
import com.debugArena.service.helpers.SmtpServerStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    private final MailService mailService;
    private final SmtpServerStatusService smtpServerStatusService;

    @Autowired
    public ContactServiceImpl(MailService mailService, SmtpServerStatusService smtpServerStatusService) {
        this.mailService = mailService;
        this.smtpServerStatusService = smtpServerStatusService;
    }

    @Override
    @EventListener(UserContactedUsEvent.class)
    public void receiveUserEmail(UserContactedUsEvent event) {

        if (!smtpServerStatusService.isSmtpServerUp()) {
            throw new EmailConnectionException();
        }

        mailService.sendToOurEmail(event.getEmailSenderBindingModel());
    }
}
