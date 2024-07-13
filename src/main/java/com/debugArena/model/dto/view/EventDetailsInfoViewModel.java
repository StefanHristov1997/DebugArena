package com.debugArena.model.dto.view;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventDetailsInfoViewModel {

    private String title;

    private String description;

    private String authorName;

    private String authorEmail;

    private LocalDateTime date;
}
