package com.debugArena.model.dto.binding;

import com.debugArena.validation.annotation.ResetPasswordMatcher;
import com.debugArena.validation.annotation.ValidUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResetPasswordMatcher
public class UserResetPasswordBindingModel {

    @NotBlank(message = "{blank_field_message}")
    @ValidUser
    @Email(message = "{email_message}")
    private String email;

    @NotBlank(message = "")
    @Size(min = 6, message = "{password_length_message}")
    private String password;

    @NotBlank(message = "")
    @Size(min = 6, message = "{password_length_message}")
    private String confirmPassword;
}
