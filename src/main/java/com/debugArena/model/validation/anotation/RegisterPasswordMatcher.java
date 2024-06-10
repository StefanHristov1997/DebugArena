package com.debugArena.model.validation.anotation;

import com.debugArena.model.validation.validator.RegisterPasswordMatcherValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = RegisterPasswordMatcherValidator.class)
public @interface RegisterPasswordMatcher {

    String message() default "{user.password-match}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
