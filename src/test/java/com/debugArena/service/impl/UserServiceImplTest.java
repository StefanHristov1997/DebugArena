package com.debugArena.service.impl;

import com.debugArena.model.dto.binding.UserEmailBindingModel;
import com.debugArena.model.dto.binding.UserProfileBindingModel;
import com.debugArena.model.dto.binding.UserRegisterBindingModel;
import com.debugArena.model.dto.binding.UserResetPasswordBindingModel;
import com.debugArena.model.dto.view.UserProfileViewModel;
import com.debugArena.model.entity.RoleEntity;
import com.debugArena.model.entity.UserEntity;
import com.debugArena.model.enums.UserRoleEnum;
import com.debugArena.repository.UserRepository;
import com.debugArena.service.RoleService;
import com.debugArena.service.helpers.LoggedUserHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserServiceImpl toTest;

    private final static String USERNAME = "testUser";
    private final static String EMAIL = "test@test.com";
    private final static String NOT_VALID_EMAIL = "test@123test.com";
    private final static String PASSWORD = "test";

    @Mock
    private UserRepository mockedUserRepository;

    @Mock
    private RoleService mockedRoleService;

    @Mock
    private LoggedUserHelper mockedLoggedUserHelper;

    @Mock
    private ApplicationEventPublisher mockedApplicationEventPublisher;

    @Mock
    private ModelMapper mockedModelMapper;

    @Mock
    private PasswordEncoder mockedPasswordEncoder;

    @BeforeEach
    void setUp() {
        this.toTest = new UserServiceImpl(
                mockedUserRepository,
                mockedRoleService,
                mockedLoggedUserHelper,
                mockedApplicationEventPublisher,
                mockedModelMapper,
                mockedPasswordEncoder);
    }

    @Test
    void testIsEmailExistReturnValidValues() {

        UserEntity testUser = new UserEntity();
        testUser.setEmail(EMAIL);

        when(mockedUserRepository
                .findByEmail(EMAIL))
                .thenReturn(Optional.of(testUser));

        boolean exist = mockedUserRepository.findByEmail(EMAIL).isEmpty();

        Assertions.assertEquals(exist, toTest.isEmailExist(EMAIL));

        when(mockedUserRepository
                .findByEmail(EMAIL))
                .thenReturn(Optional.of(new UserEntity()));

        boolean notExist = mockedUserRepository.findByEmail(EMAIL).isEmpty();

        Assertions.assertEquals(notExist, toTest.isEmailExist(EMAIL));
    }

    @Test
    void testIsUsernameExistReturnValidValues() {

        UserEntity testUser = new UserEntity();

        testUser.setUsername(USERNAME);

        when(mockedUserRepository
                .findByUsername(USERNAME))
                .thenReturn(Optional.of(testUser));

        boolean exist = mockedUserRepository.findByUsername(USERNAME).isEmpty();

        Assertions.assertEquals(exist, toTest.isUsernameExist(USERNAME));

        when(mockedUserRepository
                .findByUsername(USERNAME))
                .thenReturn(Optional.of(new UserEntity()));

        boolean notExist = mockedUserRepository.findByUsername(USERNAME).isEmpty();

        Assertions.assertEquals(notExist, toTest.isUsernameExist(USERNAME));
    }

    @Test
    void testGetUserEmails() {
        UserEntity testFirstUser = new UserEntity();
        testFirstUser.setEmail("test@1test.com");

        UserEntity testSecondUser = new UserEntity();
        testSecondUser.setEmail("test@2test.com");

        List<UserEntity> testUsers = List.of(testFirstUser, testSecondUser);

        when(mockedUserRepository
                .findAll())
                .thenReturn(testUsers);

        UserEmailBindingModel firstTestUserEmail = new UserEmailBindingModel();
        firstTestUserEmail.setEmail("test@1test.com");

        UserEmailBindingModel secondTestUserEmail = new UserEmailBindingModel();
        firstTestUserEmail.setEmail("test@2test.com");

        List<UserEmailBindingModel> testUserEmails = List.of(firstTestUserEmail, secondTestUserEmail);

        when(mockedModelMapper
                .map(testFirstUser, UserEmailBindingModel.class))
                .thenReturn(firstTestUserEmail);

        when(mockedModelMapper
                .map(testSecondUser, UserEmailBindingModel.class))
                .thenReturn(secondTestUserEmail);

        Assertions.assertEquals(2, toTest.getUserEmails().size());
        Assertions.assertEquals(testUserEmails, toTest.getUserEmails());
        Assertions.assertEquals(testUserEmails.size(), toTest.getUserEmails().size());
    }

    @Test
    void testGetUserProfile() {

        UserEntity testLoggedUser = createTestUser();

        when(mockedLoggedUserHelper.get())
                .thenReturn(testLoggedUser);

        UserProfileViewModel testUserProfile = new UserProfileViewModel();

        testUserProfile.setUsername(testLoggedUser.getUsername());
        testUserProfile.setDescription(testLoggedUser.getDescription());
        testUserProfile.setInterests(testLoggedUser.getInterests());
        testUserProfile.setSkills(testLoggedUser.getSkills());

        when(mockedModelMapper.map(testLoggedUser, UserProfileViewModel.class))
                .thenReturn(testUserProfile);

        Assertions.assertEquals(testUserProfile.getUsername(), toTest.getUserProfile().getUsername());
        Assertions.assertEquals(testUserProfile.getDescription(), toTest.getUserProfile().getDescription());
        Assertions.assertEquals(testUserProfile.getInterests(), toTest.getUserProfile().getInterests());
        Assertions.assertEquals(testUserProfile.getSkills(), toTest.getUserProfile().getSkills());
        Assertions.assertEquals(testUserProfile, toTest.getUserProfile());
    }

    @Test
    void testEditUserProfileWithValidArguments() {

        UserProfileBindingModel testUserProfile = new UserProfileBindingModel();
        testUserProfile.setDescription("test");
        testUserProfile.setSkills("test");
        testUserProfile.setInterests("test");

        UserEntity testLoggedUser = new UserEntity();

        testLoggedUser.setUsername(USERNAME);
        testLoggedUser.setDescription("");
        testLoggedUser.setInterests("");
        testLoggedUser.setSkills("");

        when(mockedLoggedUserHelper.get())
                .thenReturn(testLoggedUser);

        toTest.editProfile(testUserProfile);

        Assertions.assertEquals(testUserProfile.getDescription(), testLoggedUser.getDescription());
        Assertions.assertEquals(testUserProfile.getInterests(), testLoggedUser.getInterests());
        Assertions.assertEquals(testUserProfile.getSkills(), testLoggedUser.getSkills());
    }

    @Test
    void testEditUserProfileWithEmptyArguments() {

        UserProfileBindingModel testFirstUserProfile = new UserProfileBindingModel();
        testFirstUserProfile.setDescription("");
        testFirstUserProfile.setSkills("");
        testFirstUserProfile.setInterests("");

        UserEntity testFirstLoggedUser = createTestUser();

        when(mockedLoggedUserHelper.get())
                .thenReturn(testFirstLoggedUser);

        toTest.editProfile(testFirstUserProfile);

        Assertions.assertNotEquals(testFirstUserProfile.getDescription(), testFirstLoggedUser.getDescription());
        Assertions.assertNotEquals(testFirstUserProfile.getInterests(), testFirstLoggedUser.getInterests());
        Assertions.assertNotEquals(testFirstUserProfile.getSkills(), testFirstLoggedUser.getSkills());
    }

    @Test
    void testEditUserProfileWithBlankArguments() {

        UserProfileBindingModel testSecondUserProfile = new UserProfileBindingModel();
        testSecondUserProfile.setDescription(" ");
        testSecondUserProfile.setSkills(" ");
        testSecondUserProfile.setInterests(" ");

        UserEntity testSecondLoggedUser = createTestUser();

        when(mockedLoggedUserHelper.get())
                .thenReturn(testSecondLoggedUser);

        toTest.editProfile(testSecondUserProfile);

        Assertions.assertNotEquals(testSecondUserProfile.getDescription(), testSecondLoggedUser.getDescription());
        Assertions.assertNotEquals(testSecondUserProfile.getInterests(), testSecondLoggedUser.getInterests());
        Assertions.assertNotEquals(testSecondUserProfile.getSkills(), testSecondLoggedUser.getSkills());
    }

    @Test
    void testResetPasswordWithUserFound() {

        UserEntity testUser = createTestUser();
        UserResetPasswordBindingModel testUserResetPassword = new UserResetPasswordBindingModel();

        testUserResetPassword.setEmail(testUser.getEmail());
        testUserResetPassword.setPassword("test123");
        testUserResetPassword.setConfirmPassword("test123");

        when(mockedUserRepository.findByEmail(testUserResetPassword.getEmail()))
                .thenReturn(Optional.of(testUser));

        when(mockedPasswordEncoder.encode(testUserResetPassword.getPassword()))
                .thenReturn("test123");

        toTest.resetPassword(testUserResetPassword);

        Assertions.assertEquals(testUserResetPassword.getPassword(), testUser.getPassword());
    }

    @Test
    void testResetPasswordWithNotUserFound() {

        UserResetPasswordBindingModel testUserResetPassword = new UserResetPasswordBindingModel();

        testUserResetPassword.setEmail(NOT_VALID_EMAIL);

        Assertions
                .assertThrows(UsernameNotFoundException.class, () -> {
                    toTest.resetPassword(testUserResetPassword);
                });
    }
    @Test
    public void testRegisterUser_FirstUser() {

        UserRegisterBindingModel testUserRegisterModel = createUserRegisterModel();

        when(mockedUserRepository.count()).thenReturn(0L);

        UserEntity testUser = createTestUser();

        when(mockedModelMapper.map(testUserRegisterModel, UserEntity.class))
                .thenReturn(testUser);

        List<RoleEntity> testRoles = new ArrayList<>();
        RoleEntity testFirstRole = new RoleEntity();
        testFirstRole.setName(UserRoleEnum.USER);

        RoleEntity testSecondRole = new RoleEntity();
        testFirstRole.setName(UserRoleEnum.ADMIN);

        testRoles.add(testFirstRole);
        testRoles.add(testSecondRole);

        when(mockedRoleService.getRolesByName(List.of(UserRoleEnum.USER, UserRoleEnum.ADMIN)))
                .thenReturn(new LinkedHashSet<>(testRoles));

        toTest.registerUser(testUserRegisterModel);

        verify(mockedUserRepository, times(1)).save(testUser);
        Assertions.assertEquals(2, testUser.getRoles().size());
    }

    @Test
    public void testRegisterUser_ExistingUsers() {

        when(mockedUserRepository.count())
                .thenReturn(1L);

        UserRegisterBindingModel testUserRegisterModel = createUserRegisterModel();

        UserEntity testUser = createTestUser();

        when(mockedModelMapper.map(testUserRegisterModel, UserEntity.class)).thenReturn(testUser);

        List<RoleEntity> testRoles = new ArrayList<>();
        RoleEntity testFirstRole = new RoleEntity();
        testFirstRole.setName(UserRoleEnum.USER);
        testRoles.add(testFirstRole);

        when(mockedRoleService.getRolesByName(List.of(UserRoleEnum.USER)))
                .thenReturn(new LinkedHashSet<>(testRoles));

        toTest.registerUser(testUserRegisterModel);

        verify(mockedUserRepository, times(1)).save(testUser);
        Assertions.assertEquals(1, testUser.getRoles().size());
    }

    private static UserRegisterBindingModel createUserRegisterModel() {

        UserRegisterBindingModel testUserRegisterModel = new UserRegisterBindingModel();

        testUserRegisterModel.setUsername(USERNAME);
        testUserRegisterModel.setPassword(PASSWORD);

        return testUserRegisterModel;
    }

    private static UserEntity createTestUser() {

        UserEntity testUser = new UserEntity();

        testUser.setUsername(USERNAME);
        testUser.setEmail(EMAIL);
        testUser.setPassword(PASSWORD);
        testUser.setDescription("test");
        testUser.setInterests("test");
        testUser.setSkills("test");

        return testUser;
    }

}