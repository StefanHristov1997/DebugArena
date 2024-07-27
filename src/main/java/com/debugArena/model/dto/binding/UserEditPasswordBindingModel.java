package com.debugArena.model.dto.binding;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditPasswordBindingModel {

    @NotBlank(message = "")
    @Size(min = 6, message = "{password_length_message}")
    private String password;
}
