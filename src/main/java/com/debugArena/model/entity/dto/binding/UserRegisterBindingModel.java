package com.debugArena.model.entity.dto.binding;

import com.debugArena.model.validation.anotation.PasswordMatcher;
import com.debugArena.model.validation.anotation.UniqueEmail;
import com.debugArena.model.validation.anotation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
@PasswordMatcher
public class UserRegisterBindingModel {

    @NotBlank
    @Length(min = 5, message = "{user.username.length}")
    @UniqueUsername
    private String username;

    @NotBlank
    @Email(message = "{user.email}")
    @UniqueEmail
    private String email;

    @NotBlank
    @Length(min = 6, message = "{user.password.length}")
    private String password;

    @NotBlank
    @Length(min = 6, message = "{user.confirm-password.length}")
    private String confirmPassword;
}
