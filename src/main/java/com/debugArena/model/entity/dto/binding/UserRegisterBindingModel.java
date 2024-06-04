package com.debugArena.model.entity.dto.binding;

import com.debugArena.model.validation.anotation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
public class UserRegisterBindingModel {

    @NotBlank
    @Length(min = 3, message = "{user.username.length}")
    @UniqueUsername
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;
}
