package com.debugArena.model.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class UserContactedUsEvent extends ApplicationEvent {

    private final String userEmail;

    public UserContactedUsEvent(Object source, String userEmail) {
        super(source);
        this.userEmail = userEmail;
    }
}
