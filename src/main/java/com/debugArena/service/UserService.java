package com.debugArena.service;

import com.debugArena.model.entity.dto.binding.UserRegisterBindingModel;

public interface UserService {

    boolean isUsernameExist(String username);

    boolean isEmailExist(String email);

    void registerUser(UserRegisterBindingModel user);
}
