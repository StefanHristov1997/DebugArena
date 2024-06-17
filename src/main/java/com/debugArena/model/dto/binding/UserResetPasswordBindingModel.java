package com.debugArena.model.dto.binding;

import com.debugArena.model.validation.anotation.ResetPasswordMatcher;
import com.debugArena.model.validation.anotation.ValidUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResetPasswordMatcher
public class UserResetPasswordBindingModel {

    @NotBlank(message = "{not.blank}")
    @ValidUser
    @Email(message = "{user.email}")
    private String email;

    @NotBlank(message = "{not.blank}")
    @Size(min = 6, message = "{user.password.length}")
    private String password;

    @NotBlank(message = "{not.blank}")
    @Size(min = 6, message = "{user.password.length}")
    private String confirmPassword;
}
