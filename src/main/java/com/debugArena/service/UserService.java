package com.debugArena.service;

import com.debugArena.model.dto.binding.*;
import com.debugArena.model.dto.view.UserProfileViewModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    boolean isUsernameExist(String username);

    boolean isEmailExist(String email);

    void registerUser(UserRegisterBindingModel user);

    void resetPassword(UserResetPasswordBindingModel userResetPasswordBindingModel);

    void contactUs(EmailSenderBindingModel emailSenderBindingModel);

    void editProfile(UserProfileBindingModel userProfileBindingModel);

    void editUserUsername(UserEditUsernameBindingModel userEditUsernameBindingModel);

    void editUserPassword(UserEditPasswordBindingModel userPasswordBindingModel);

    void uploadProfileImage(MultipartFile file);

    UserProfileViewModel getUserProfile();

    List<UserEmailBindingModel> getUserEmails();



}
