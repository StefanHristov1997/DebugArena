package com.debugArena.model.dto.binding;

import com.debugArena.validation.annotation.PostImageSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserEditProfileImageBindingModel {

    @PostImageSize(message = "{invalid.image.size}")
    private MultipartFile profileImage;
}
