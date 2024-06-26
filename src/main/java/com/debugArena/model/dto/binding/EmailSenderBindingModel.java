package com.debugArena.model.dto.binding;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import static messages.ValidationErrorMessages.BLANK_FIELD_MESSAGE;
import static messages.ValidationErrorMessages.EMAIL_MESSAGE;

@Getter
@Setter
public class EmailSenderBindingModel {

    @NotBlank(message = BLANK_FIELD_MESSAGE)
    private String fullName;

    @NotBlank(message = BLANK_FIELD_MESSAGE)
    @Email(message = EMAIL_MESSAGE)
    private String email;

    @NotBlank(message = BLANK_FIELD_MESSAGE)
    private String message;
}
