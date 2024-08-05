package com.debugArena.validation.annotation;

import com.debugArena.validation.validator.ResetPasswordMatcherValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = ResetPasswordMatcherValidator.class)
public @interface ResetPasswordMatcher {

    String message() default "{passwords.not.match}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
