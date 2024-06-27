package com.debugArena.model.dto.binding;

import com.debugArena.model.validation.annotation.ResetPasswordMatcher;
import com.debugArena.model.validation.annotation.ValidUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import static messages.ValidationErrorMessages.*;

@Getter
@Setter
@ResetPasswordMatcher
public class UserResetPasswordBindingModel {

    @NotBlank(message = BLANK_FIELD_MESSAGE)
    @ValidUser
    @Email(message = EMAIL_MESSAGE)
    private String email;

    @NotBlank(message = BLANK_FIELD_MESSAGE)
    @Size(min = 6, message = PASSWORD_LENGTH_MESSAGE)
    private String password;

    @NotBlank(message = BLANK_FIELD_MESSAGE)
    @Size(min = 6, message = PASSWORD_LENGTH_MESSAGE)
    private String confirmPassword;
}
