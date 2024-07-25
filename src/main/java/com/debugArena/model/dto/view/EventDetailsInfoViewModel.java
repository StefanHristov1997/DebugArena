package com.debugArena.model.dto.view;

import com.debugArena.model.enums.VideoPlatformEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventDetailsInfoViewModel {

    private Long id;

    private String title;

    private String description;

    private String authorName;

    private String authorEmail;

    private LocalDateTime date;

    private VideoPlatformEnum platform;
}
