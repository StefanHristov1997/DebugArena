package com.debugArena.service;

import com.debugArena.model.dto.binding.UserRegisterBindingModel;
import com.debugArena.model.dto.binding.UserResetPasswordBindingModel;

public interface UserService {

    boolean isUsernameExist(String username);

    boolean isEmailExist(String email);

    void registerUser(UserRegisterBindingModel user);

    void resetPassword(UserResetPasswordBindingModel userResetPasswordBindingModel);
}
