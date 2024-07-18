package com.debugArena.service.impl;

import com.debugArena.model.entity.RoleEntity;
import com.debugArena.model.entity.UserEntity;
import com.debugArena.model.enums.UserRoleEnum;
import com.debugArena.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.LinkedHashSet;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository mockedUserRepository;

    private UserDetailsServiceImpl toTest;

    private final String VALID_EMAIL = "test@test.com";
    private final String NOT_VALID_EMAIL = "test@test.com";

    @BeforeEach
    void setUp() {
        toTest = new UserDetailsServiceImpl(mockedUserRepository);
    }

    @Test
    void testLoadUserByUsername_UserFound() {

        UserEntity testUser = new UserEntity();

        testUser.setUsername("testUser");
        testUser.setPassword("password");
        testUser.setEmail(VALID_EMAIL);
        testUser.setRoles(new LinkedHashSet<>());
        testUser.getRoles().add(new RoleEntity(1L, UserRoleEnum.ADMIN));
        testUser.getRoles().add(new RoleEntity(1L, UserRoleEnum.USER));

       when(mockedUserRepository.findByEmail(VALID_EMAIL))
                .thenReturn(Optional.of(testUser));


        UserDetails userDetails = toTest.loadUserByUsername(VALID_EMAIL);

        Assertions.assertInstanceOf(UserDetails.class, userDetails);
        Assertions.assertEquals(testUser.getEmail(), userDetails.getUsername());
        Assertions.assertEquals(testUser.getPassword(), userDetails.getPassword());

        var expectedRoles = testUser.getRoles().stream().map(r -> "ROLE_" + r.getName().name()).toList();
        var actualRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        Assertions.assertEquals(expectedRoles, actualRoles);

    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        Assertions.
                assertThrows(UsernameNotFoundException.class, () -> {
                    toTest.loadUserByUsername(NOT_VALID_EMAIL);
                });
    }
}