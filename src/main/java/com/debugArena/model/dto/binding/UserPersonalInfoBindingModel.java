package com.debugArena.model.dto.binding;

import com.debugArena.model.validation.annotation.UniqueEmail;
import com.debugArena.model.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPersonalInfoBindingModel {

    @NotBlank(message = "")
    @Size(min = 5, message = "{username.length.message}")
    @UniqueUsername
    private String username;

    @NotBlank(message = "{blank_field_message}")
    @Email(message = "{email_message}")
    @UniqueEmail
    private String email;

    @NotBlank(message = "")
    @Size(min = 6, message = "{password_length_message}")
    private String password;
}
