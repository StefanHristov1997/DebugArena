package com.debugArena.model.dto.binding;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmailSenderBindingModel {

    @NotBlank(message = "{blank_field_message}")
    private String fullName;

    @NotBlank(message = "{blank_field_message}")
    @Email(message = "{email_message}")
    private String email;

    @NotBlank(message ="{blank_field_message}")
    private String message;
}
