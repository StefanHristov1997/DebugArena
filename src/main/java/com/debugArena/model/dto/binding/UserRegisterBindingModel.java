package com.debugArena.model.dto.binding;

import com.debugArena.model.validation.anotation.RegisterPasswordMatcher;
import com.debugArena.model.validation.anotation.UniqueEmail;
import com.debugArena.model.validation.anotation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
@RegisterPasswordMatcher
public class UserRegisterBindingModel {

    @NotBlank(message = "{not.blank}")
    @Length(min = 5, message = "{user.username.length}")
    @UniqueUsername
    private String username;

    @NotBlank(message = "{not.blank}")
    @Email(message = "{user.email}")
    @UniqueEmail
    private String email;

    @NotBlank(message = "{not.blank}")
    @Length(min = 6, message = "{user.password.length}")
    private String password;

    @NotBlank(message = "{not.blank}")
    @Length(min = 6, message = "{user.confirm-password.length}")
    private String confirmPassword;
}
