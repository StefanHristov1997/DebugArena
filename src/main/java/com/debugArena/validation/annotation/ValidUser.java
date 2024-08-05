package com.debugArena.validation.annotation;

import com.debugArena.validation.validator.ValidUserValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidUserValidator.class)
public @interface ValidUser {

    String message() default "{account.not.exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
