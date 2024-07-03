package com.debugArena.model.dto.binding;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static messages.ValidationErrorMessages.COMMENT_NOT_BLANK_MESSAGE;

@Getter
@Setter
public class AddCommentBindingModel {

    @NotBlank(message = COMMENT_NOT_BLANK_MESSAGE)
    private String textContent;

    private LocalDate createdOn;

    public AddCommentBindingModel() {
        this.createdOn = LocalDate.now();
    }
}
