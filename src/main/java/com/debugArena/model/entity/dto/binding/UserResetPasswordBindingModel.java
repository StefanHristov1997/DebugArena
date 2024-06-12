package com.debugArena.model.entity.dto.binding;

import com.debugArena.model.validation.anotation.ResetPasswordMatcher;
import com.debugArena.model.validation.anotation.ValidUser;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ResetPasswordMatcher
public class UserResetPasswordBindingModel {

    @ValidUser
    @Email(message = "{user.email}")
    private String email;


    @Length(min = 6, message = "{user.password.length}")
    private String password;

    @Length(min = 6, message = "{user.password.length}")
    private String confirmPassword;
}
