package com.debugArena.service;

import com.debugArena.model.dto.binding.EmailSenderBindingModel;
import com.debugArena.model.dto.binding.UserProfileBindingModel;
import com.debugArena.model.dto.binding.UserRegisterBindingModel;
import com.debugArena.model.dto.binding.UserResetPasswordBindingModel;
import com.debugArena.model.dto.view.UserProfileViewModel;

public interface UserService {

    boolean isUsernameExist(String username);

    boolean isEmailExist(String email);

    void registerUser(UserRegisterBindingModel user);

    void resetPassword(UserResetPasswordBindingModel userResetPasswordBindingModel);

    void contactUs(EmailSenderBindingModel emailSenderBindingModel);

    void editProfile(UserProfileBindingModel userProfileBindingModel);

    UserProfileViewModel getUserProfile();

}
