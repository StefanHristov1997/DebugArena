package com.debugArena.service.impl;

import com.debugArena.model.entity.RoleEntity;
import com.debugArena.model.enums.UserRoleEnum;
import com.debugArena.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    private RoleServiceImpl toTest;

    private final List<UserRoleEnum> roles = new ArrayList<>();

    @Mock
    private RoleRepository mockedRoleRepository;

    @BeforeEach
    void setUp() {
        this.toTest = new RoleServiceImpl(mockedRoleRepository);
        roles.add(UserRoleEnum.ADMIN);
        roles.add(UserRoleEnum.USER);
    }

    @Test
    void testGetRolesByName() {

        RoleEntity firstRole = new RoleEntity(1L, UserRoleEnum.USER);
        RoleEntity secondRole = new RoleEntity(1L, UserRoleEnum.ADMIN);

        when(mockedRoleRepository.findAllByNameIn(roles))
                .thenReturn(Set.of(firstRole, secondRole)
                );

        Set<RoleEntity> toTestRoles = toTest.getRolesByName(roles);

        Assertions.assertTrue(toTestRoles.contains(firstRole));
        Assertions.assertTrue(toTestRoles.contains(secondRole));
        Assertions.assertEquals(2, toTestRoles.size());
    }

}