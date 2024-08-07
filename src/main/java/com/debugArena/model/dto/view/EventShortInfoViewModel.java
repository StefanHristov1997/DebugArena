package com.debugArena.model.dto.view;

import com.debugArena.model.enums.VideoPlatformEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventShortInfoViewModel {

    private Long id;

    private String title;

    private String authorName;

    private String authorEmail;

    private String authorImageURL;

    private VideoPlatformEnum platform;

    private LocalDateTime date;

}
