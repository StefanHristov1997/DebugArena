package com.debugArena.model.dto.view;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentViewModel {

    private String authorUsername;

    private String textContent;

    private LocalDate createdOn;
}
