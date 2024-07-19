package com.debugArena.service.impl;

import com.debugArena.events.UserContactedUsEvent;
import com.debugArena.model.dto.binding.*;
import com.debugArena.model.dto.view.UserProfileViewModel;
import com.debugArena.model.entity.UserEntity;
import com.debugArena.model.enums.UserRoleEnum;
import com.debugArena.repository.UserRepository;
import com.debugArena.service.RoleService;
import com.debugArena.service.UserService;
import com.debugArena.service.helpers.LoggedUserHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    private final LoggedUserHelper loggedUserHelper;
    private final ApplicationEventPublisher publisher;

    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            RoleService roleService,
            LoggedUserHelper loggedUserHelper,
            ApplicationEventPublisher publisher,
            ModelMapper mapper,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.loggedUserHelper = loggedUserHelper;
        this.publisher = publisher;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserRegisterBindingModel userRegisterBindingModel) {

        final UserEntity userToSave = mapper.map(userRegisterBindingModel, UserEntity.class);

        if (userRepository.count() == 0) {
            userToSave.setRoles(roleService.getRolesByName(
                    List.of(
                            UserRoleEnum.USER,
                            UserRoleEnum.ADMIN)));
        } else {
            userToSave.setRoles(roleService.getRolesByName(
                    List.of(UserRoleEnum.USER)));
        }

        userRepository.save(userToSave);
    }

    @Override
    public void resetPassword(UserResetPasswordBindingModel userResetPasswordBindingModel) {

        final UserEntity userToSave = this.userRepository
                .findByEmail(userResetPasswordBindingModel
                        .getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with email: " + userResetPasswordBindingModel.getEmail() + " is not found"));

        final String encodedPassword = passwordEncoder.encode(userResetPasswordBindingModel.getPassword());

        userToSave.setPassword(encodedPassword);

        userRepository.save(userToSave);
    }

    @Override
    public void contactUs(EmailSenderBindingModel emailSenderBindingModel) {
        publisher.publishEvent(new UserContactedUsEvent("Contact Service", emailSenderBindingModel));
    }

    @Override
    public void editProfile(UserProfileBindingModel userProfileBindingModel) {

        UserEntity currentUser = loggedUserHelper.get();

        String userDescription = userProfileBindingModel.getDescription();

        if (!userDescription.isBlank()) {
            currentUser.setDescription(userDescription);
        }

        String userSkills = userProfileBindingModel.getSkills();

        if (!userSkills.isBlank()) {
            currentUser.setSkills(userSkills);
        }

        String userInterests = userProfileBindingModel.getInterests();

        if (!userInterests.isBlank()) {
            currentUser.setInterests(userInterests);
        }

        userRepository.saveAndFlush(currentUser);
    }

    @Override
    public UserProfileViewModel getUserProfile() {
        return mapper.map(loggedUserHelper.get(), UserProfileViewModel.class);
    }

    @Override
    public List<UserEmailBindingModel> getUserEmails() {
        List<UserEntity> allUsers = userRepository.findAll();

        return allUsers
                .stream()
                .map(user ->
                        mapper.map(user, UserEmailBindingModel.class)).toList();
    }

    @Override
    public boolean isUsernameExist(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    @Override
    public boolean isEmailExist(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }
}
