package com.debugArena.validation.validator;

import com.debugArena.validation.annotation.PostImageSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ImageSizeValidation implements ConstraintValidator<PostImageSize, MultipartFile> {

    private long maxSize;

    @Override
    public void initialize(PostImageSize constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile,
                           ConstraintValidatorContext cxt) {

        return multipartFile == null || multipartFile.getSize() <=
                maxSize;
    }
}
