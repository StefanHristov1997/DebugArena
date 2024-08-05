package com.debugArena.validation.validator;

import com.debugArena.validation.annotation.UniqueUsername;
import com.debugArena.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private String message;
    private final UserService userService;

    @Autowired
    public UniqueUsernameValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        if (username == null) {
            return true;
        } else {
            boolean isUsernameAlreadyExist = this.userService.isUsernameExist(username);

            if (isUsernameAlreadyExist) {
                replaceDefaultConstraintViolation(context, message);
            }

            return isUsernameAlreadyExist;
        }
    }

    private void replaceDefaultConstraintViolation(ConstraintValidatorContext context, String message) {
        context
                .unwrap(HibernateConstraintValidatorContext.class)
                .buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }
}
