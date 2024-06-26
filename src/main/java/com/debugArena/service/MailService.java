package com.debugArena.service;

import com.debugArena.model.dto.binding.EmailSenderBindingModel;

public interface MailService {

    void receiveEmail(EmailSenderBindingModel emailSenderBindingModel);
}
