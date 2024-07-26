package com.debugArena.model.dto.binding;

import com.debugArena.model.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditUsernameBindingModel {

    @NotBlank(message = "")
    @Size(min = 5, message = "{username.length.message}")
    @UniqueUsername
    private String username;
}
