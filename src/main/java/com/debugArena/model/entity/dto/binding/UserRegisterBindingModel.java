package com.debugArena.model.entity.dto.binding;

import com.debugArena.model.validation.anotation.RegisterPasswordMatcher;
import com.debugArena.model.validation.anotation.UniqueEmail;
import com.debugArena.model.validation.anotation.UniqueUsername;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
@RegisterPasswordMatcher
public class UserRegisterBindingModel {

    @Length(min = 5, message = "{user.username.length}")
    @UniqueUsername
    private String username;


    @Email(message = "{user.email}")
    @UniqueEmail
    private String email;

    @Length(min = 6, message = "{user.password.length}")
    private String password;


    @Length(min = 6, message = "{user.confirm-password.length}")
    private String confirmPassword;
}
