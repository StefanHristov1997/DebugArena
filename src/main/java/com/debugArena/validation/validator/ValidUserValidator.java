package com.debugArena.validation.validator;

import com.debugArena.validation.annotation.ValidUser;
import com.debugArena.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidUserValidator implements ConstraintValidator<ValidUser, String> {

    String message;

    private final UserRepository userRepository;

    @Autowired
    public ValidUserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(ValidUser constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        if (email == null) {
            return true;
        } else {
            boolean isUserExist = userRepository.findByEmail(email).isPresent();

            if (!isUserExist) {
                replaceDefaultConstrainViolation(context, message);
            }

            return isUserExist;
        }
    }

    public void replaceDefaultConstrainViolation(ConstraintValidatorContext context, String message) {
        context
                .unwrap(HibernateConstraintValidatorContext.class)
                .buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }
}
