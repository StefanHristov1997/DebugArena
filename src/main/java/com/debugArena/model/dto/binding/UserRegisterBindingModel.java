package com.debugArena.model.dto.binding;

import com.debugArena.model.validation.anotation.RegisterPasswordMatcher;
import com.debugArena.model.validation.anotation.UniqueEmail;
import com.debugArena.model.validation.anotation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import messages.ValidationErrorMessages;
import org.modelmapper.spi.ErrorMessage;


@Getter
@Setter
@RegisterPasswordMatcher
public class UserRegisterBindingModel {

    @NotBlank(message = ValidationErrorMessages.BLANK_FIELD_MESSAGE)
    @Size(min = 5, message = ValidationErrorMessages.USERNAME_LENGTH_MESSAGE)
    @UniqueUsername
    private String username;

    @NotBlank(message = ValidationErrorMessages.BLANK_FIELD_MESSAGE)
    @Email(message = ValidationErrorMessages.EMAIL_MESSAGE)
    @UniqueEmail
    private String email;

    @NotBlank(message = ValidationErrorMessages.BLANK_FIELD_MESSAGE)
    @Size(min = 6, message = ValidationErrorMessages.PASSWORD_LENGTH_MESSAGE)
    private String password;

    @NotBlank(message = ValidationErrorMessages.BLANK_FIELD_MESSAGE)
    @Size(min = 6, message = ValidationErrorMessages.PASSWORD_LENGTH_MESSAGE)
    private String confirmPassword;
}
