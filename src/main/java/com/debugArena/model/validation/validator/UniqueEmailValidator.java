package com.debugArena.model.validation.validator;

import com.debugArena.model.validation.anotation.UniqueEmail;
import com.debugArena.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private String message;

    private final UserService userService;

    @Autowired
    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true;
        } else {
            boolean isEmailAlreadyExist = userService.isEmailExist(email);

            if (isEmailAlreadyExist) {
                replaceDefaultConstraintViolation(context, message);
            }

            return isEmailAlreadyExist;
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
