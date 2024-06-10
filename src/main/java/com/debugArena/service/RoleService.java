package com.debugArena.service;

import com.debugArena.model.entity.RoleEntity;
import com.debugArena.model.entity.enums.UserRoleEnum;

import java.util.List;
import java.util.Set;

public interface RoleService {

    Set<RoleEntity> getRolesByName(List<UserRoleEnum> roles);
}
