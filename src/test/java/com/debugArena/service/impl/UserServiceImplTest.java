package com.debugArena.service.impl;

import com.debugArena.model.dto.binding.UserEmailBindingModel;
import com.debugArena.model.dto.binding.UserProfileBindingModel;
import com.debugArena.model.dto.view.UserProfileViewModel;
import com.debugArena.model.entity.UserEntity;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserServiceImpl toTest;

    private final String EMAIL = "test@test.com";
    private final String USERNAME = "testUser";

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

        UserEntity testLoggedUser = new UserEntity();

        testLoggedUser.setUsername(USERNAME);
        testLoggedUser.setDescription("test");
        testLoggedUser.setInterests("test");
        testLoggedUser.setSkills("test");

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

        UserEntity testFirstLoggedUser = new UserEntity();

        testFirstLoggedUser.setUsername(USERNAME);
        testFirstLoggedUser.setDescription("test");
        testFirstLoggedUser.setInterests("test");
        testFirstLoggedUser.setSkills("test");

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

        UserEntity testSecondLoggedUser = new UserEntity();

        testSecondLoggedUser.setUsername(USERNAME);
        testSecondLoggedUser.setDescription("test");
        testSecondLoggedUser.setInterests("test");
        testSecondLoggedUser.setSkills("test");

        when(mockedLoggedUserHelper.get())
                .thenReturn(testSecondLoggedUser);

        toTest.editProfile(testSecondUserProfile);

        Assertions.assertNotEquals(testSecondUserProfile.getDescription(), testSecondLoggedUser.getDescription());
        Assertions.assertNotEquals(testSecondUserProfile.getInterests(), testSecondLoggedUser.getInterests());
        Assertions.assertNotEquals(testSecondUserProfile.getSkills(), testSecondLoggedUser.getSkills());
    }



}