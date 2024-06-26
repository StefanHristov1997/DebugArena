package com.debugArena.model.events;

import com.debugArena.model.dto.binding.EmailSenderBindingModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class UserContactedUsEvent extends ApplicationEvent {

    private final EmailSenderBindingModel emailSenderBindingModel;

    public UserContactedUsEvent(Object source, EmailSenderBindingModel emailSenderBindingModel) {
        super(source);
        this.emailSenderBindingModel = emailSenderBindingModel;
    }
}
