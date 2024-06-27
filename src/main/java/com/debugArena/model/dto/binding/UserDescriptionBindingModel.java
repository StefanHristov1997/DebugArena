package com.debugArena.model.dto.binding;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import static messages.ValidationErrorMessages.BLANK_FIELD_MESSAGE;

@Getter
@Setter
public class UserDescriptionBindingModel {

    @NotBlank(message = BLANK_FIELD_MESSAGE)
    private String description;
}
