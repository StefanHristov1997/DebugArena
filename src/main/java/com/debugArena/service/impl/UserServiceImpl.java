package com.debugArena.service.impl;

import com.debugArena.model.entity.UserEntity;
import com.debugArena.model.entity.dto.binding.UserRegisterBindingModel;
import com.debugArena.model.entity.enums.UserRoleEnum;
import com.debugArena.repository.UserRepository;
import com.debugArena.service.RoleService;
import com.debugArena.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mapper = mapper;
    }

    @Override
    public void registerUser(UserRegisterBindingModel userRegisterBindingModel) {
        UserEntity userToSave = mapper.map(userRegisterBindingModel, UserEntity.class);

        this.userRepository.save(userToSave);
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
