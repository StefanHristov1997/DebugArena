package com.debugArena.model.validation.validator;

import com.debugArena.model.dto.binding.UserResetPasswordBindingModel;
import com.debugArena.model.validation.annotation.ResetPasswordMatcher;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

public class ResetPasswordMatcherValidator implements ConstraintValidator<ResetPasswordMatcher, UserResetPasswordBindingModel> {

    private String message;


    @Override
    public void initialize(ResetPasswordMatcher constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(UserResetPasswordBindingModel userResetPasswordBindingModel, ConstraintValidatorContext context) {
        if (userResetPasswordBindingModel == null) {
            return true;
        } else {
            final String password = userResetPasswordBindingModel.getPassword();
            final String confirmedPassword = userResetPasswordBindingModel.getConfirmPassword();

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
