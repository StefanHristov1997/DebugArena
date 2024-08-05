package com.debugArena.validation.annotation;

import com.debugArena.validation.validator.ImageSizeValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Constraint(validatedBy = ImageSizeValidation.class)
@Documented
public @interface PostImageSize {

    String message() default "Invalid image size";

    long maxSize() default 2 * 1024 * 1024;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
