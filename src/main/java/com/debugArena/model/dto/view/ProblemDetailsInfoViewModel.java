package com.debugArena.model.dto.view;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProblemDetailsInfoViewModel {

    private String title;

    private String description;

    private String authorUsername;

    private LocalDate createdOn;
}
