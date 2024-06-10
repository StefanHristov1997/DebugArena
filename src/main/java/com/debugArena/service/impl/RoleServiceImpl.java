package com.debugArena.service.impl;

import com.debugArena.model.entity.RoleEntity;
import com.debugArena.model.entity.enums.UserRoleEnum;
import com.debugArena.repository.RoleRepository;
import com.debugArena.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<RoleEntity> getRolesByName(List<UserRoleEnum> roles) {
        return this.roleRepository
                .findAllByNameIn
                        (roles);
    }

}
