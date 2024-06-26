package com.debugArena.model.dto.binding;

import com.debugArena.model.validation.anotation.RegisterPasswordMatcher;
import com.debugArena.model.validation.anotation.UniqueEmail;
import com.debugArena.model.validation.anotation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import static messages.ValidationErrorMessages.*;


@Getter
@Setter
@RegisterPasswordMatcher
public class UserRegisterBindingModel {

    @NotBlank(message = BLANK_FIELD_MESSAGE)
    @Size(min = 5, message = USERNAME_LENGTH_MESSAGE)
    @UniqueUsername
    private String username;

    @NotBlank(message = BLANK_FIELD_MESSAGE)
    @Email(message = EMAIL_MESSAGE)
    @UniqueEmail
    private String email;

    @NotBlank(message = BLANK_FIELD_MESSAGE)
    @Size(min = 6, message = PASSWORD_LENGTH_MESSAGE)
    private String password;

    @NotBlank(message = BLANK_FIELD_MESSAGE)
    @Size(min = 6, message = PASSWORD_LENGTH_MESSAGE)
    private String confirmPassword;
}
