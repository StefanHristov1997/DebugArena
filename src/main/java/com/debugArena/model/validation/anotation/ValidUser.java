package com.debugArena.model.validation.anotation;

import com.debugArena.model.validation.validator.ValidUserValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import messages.ValidationErrorMessages;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidUserValidator.class)
public @interface ValidUser {

    String message() default ValidationErrorMessages.ACCOUNT_NOT_EXIST_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
