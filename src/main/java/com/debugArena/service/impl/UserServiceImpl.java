package com.debugArena.service.impl;

import com.debugArena.model.dto.binding.EmailSenderBindingModel;
import com.debugArena.model.dto.binding.UserDescriptionBindingModel;
import com.debugArena.model.entity.UserEntity;
import com.debugArena.model.dto.binding.UserRegisterBindingModel;
import com.debugArena.model.dto.binding.UserResetPasswordBindingModel;
import com.debugArena.model.enums.UserRoleEnum;
import com.debugArena.model.events.UserContactedUsEvent;
import com.debugArena.repository.UserRepository;
import com.debugArena.service.RoleService;
import com.debugArena.service.UserService;
import com.debugArena.service.helpers.LoggedUserHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, LoggedUserHelper loggedUserHelper, ApplicationEventPublisher publisher, ModelMapper mapper, PasswordEncoder passwordEncoder) {
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
                            UserRoleEnum.MODERATOR,
                            UserRoleEnum.ADMIN)));
        } else {
            userToSave.setRoles(roleService.getRolesByName(
                    List.of(UserRoleEnum.USER)));
        }

        this.userRepository.save(userToSave);
    }

    @Override
    public void resetPassword(UserResetPasswordBindingModel userResetPasswordBindingModel) {

        final UserEntity userToSave = this.userRepository.findByEmail(userResetPasswordBindingModel.getEmail()).get();

        final String encodedPassword = passwordEncoder.encode(userResetPasswordBindingModel.getPassword());

        userToSave.setPassword(encodedPassword);

        this.userRepository.save(userToSave);
    }

    @Override
    public void contactUs(EmailSenderBindingModel emailSenderBindingModel) {

        publisher.publishEvent(new UserContactedUsEvent("Contact Service", emailSenderBindingModel));
    }

    @Override
    public void editUserDescription(UserDescriptionBindingModel userDescriptionBindingModel) {

        UserEntity currentUser = loggedUserHelper.get();
        currentUser.setDescription(userDescriptionBindingModel.getDescription());

        this.userRepository.save(currentUser);
    }

    @Override
    public boolean isUsernameExist(String username) {
        return this.userRepository.findByUsername(username).isEmpty();
    }

    @Override
    public boolean isEmailExist(String email) {
        return this.userRepository.findByEmail(email).isEmpty();
    }
}
