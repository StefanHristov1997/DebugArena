package com.debugArena.model.dto.view;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProblemShortInfoViewModel {

    private Long id;

    private String title;

    private String authorUsername;

    private LocalDate createdOn;
}
