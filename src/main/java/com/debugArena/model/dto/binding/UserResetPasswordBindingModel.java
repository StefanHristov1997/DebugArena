package com.debugArena.model.dto.binding;

import com.debugArena.model.validation.anotation.ResetPasswordMatcher;
import com.debugArena.model.validation.anotation.ValidUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import messages.ValidationErrorMessages;

@Getter
@Setter
@ResetPasswordMatcher
public class UserResetPasswordBindingModel {

    @NotBlank(message = ValidationErrorMessages.BLANK_FIELD_MESSAGE)
    @ValidUser
    @Email(message = ValidationErrorMessages.EMAIL_MESSAGE)
    private String email;

    @NotBlank(message = ValidationErrorMessages.BLANK_FIELD_MESSAGE)
    @Size(min = 6, message = ValidationErrorMessages.PASSWORD_LENGTH_MESSAGE)
    private String password;

    @NotBlank(message = ValidationErrorMessages.BLANK_FIELD_MESSAGE)
    @Size(min = 6, message = ValidationErrorMessages.PASSWORD_LENGTH_MESSAGE)
    private String confirmPassword;
}
