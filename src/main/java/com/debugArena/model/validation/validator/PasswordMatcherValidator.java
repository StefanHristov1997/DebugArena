package com.debugArena.model.validation.validator;

import com.debugArena.model.entity.dto.binding.UserRegisterBindingModel;
import com.debugArena.model.validation.anotation.PasswordMatcher;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

public class PasswordMatcherValidator implements ConstraintValidator<PasswordMatcher, UserRegisterBindingModel> {

    private String message;

    @Override
    public void initialize(PasswordMatcher constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(UserRegisterBindingModel userRegisterBindingModel, ConstraintValidatorContext context) {


        if (userRegisterBindingModel == null) {
            return true;
        } else {
            final String password = userRegisterBindingModel.getPassword();
            final String confirmedPassword = userRegisterBindingModel.getConfirmPassword();

            boolean isPasswordMatch = password != null && password.equals(confirmedPassword);

            if (!isPasswordMatch) {
                HibernateConstraintValidatorContext hibernateContext =
                        context.unwrap(HibernateConstraintValidatorContext.class);

                hibernateContext
                        .buildConstraintViolationWithTemplate(message)
                        .addPropertyNode("confirmPassword")
                        .addConstraintViolation()
                        .disableDefaultConstraintViolation();
            }

            return isPasswordMatch;
        }
    }
}
