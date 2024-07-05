package com.debugArena.model.dto.binding;

import com.debugArena.model.validation.annotation.ResetPasswordMatcher;
import com.debugArena.model.validation.annotation.ValidUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import static messages.ValidationErrorMessages.EMAIL_MESSAGE;
import static messages.ValidationErrorMessages.PASSWORD_LENGTH_MESSAGE;

@Getter
@Setter
@ResetPasswordMatcher
public class UserResetPasswordBindingModel {

    @NotBlank(message = "{blank_field_message}")
    @ValidUser
    @Email(message = "{email_message}")
    private String email;

    @NotBlank(message = "{blank_field_message}")
    @Size(min = 6, message = "{password_length_message}")
    private String password;

    @NotBlank(message = "{blank_field_message}")
    @Size(min = 6, message = "{password_length_message}")
    private String confirmPassword;
}
