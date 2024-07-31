package com.debugArena.model.dto.binding;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileBindingModel {

    private String username;

    private String imageURL;

    private String description;

    private String skills;

    private String interests;
}
