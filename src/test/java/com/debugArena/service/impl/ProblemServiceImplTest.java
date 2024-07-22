package com.debugArena.service.impl;

import com.debugArena.exeption.ObjectNotFoundException;
import com.debugArena.model.dto.binding.AddProblemBindingModel;
import com.debugArena.model.dto.view.DailyNotificationProblemViewModel;
import com.debugArena.model.dto.view.ProblemDetailsInfoViewModel;
import com.debugArena.model.dto.view.ProblemShortInfoViewModel;
import com.debugArena.model.entity.LanguageEntity;
import com.debugArena.model.entity.ProblemEntity;
import com.debugArena.model.entity.UserEntity;
import com.debugArena.model.enums.LanguageEnum;
import com.debugArena.repository.LanguageRepository;
import com.debugArena.repository.ProblemRepository;
import com.debugArena.repository.UserRepository;
import com.debugArena.service.ProblemService;
import com.debugArena.service.helpers.LoggedUserHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProblemServiceImplTest {

    private ProblemService toTest;

    @Captor
    private ArgumentCaptor<ProblemEntity> problemCaptor;

    @Captor
    private ArgumentCaptor<UserEntity> userCaptor;

    @Mock
    private ProblemRepository mockedProblemRepository;

    @Mock
    private UserRepository mockedUserRepository;

    @Mock
    private LanguageRepository mockedLanguageRepository;

    @Mock
    private LoggedUserHelper mockedLoggedUserHelper;

    @Mock
    private ModelMapper mockedModelMapper;

    @BeforeEach
    void setUp() {
        this.toTest = new ProblemServiceImpl(
                mockedProblemRepository,
                mockedUserRepository,
                mockedLanguageRepository,
                mockedLoggedUserHelper,
                mockedModelMapper);
    }

    @Test
    void testAddProblemLanguage_found() {

        AddProblemBindingModel testAddProblemBindingModel = createAddProblemBindingModel();
        ProblemEntity testProblem = createProblem();

        when(mockedModelMapper.map(testAddProblemBindingModel, ProblemEntity.class))
                .thenReturn(testProblem);

        LanguageEntity testLanguage = new LanguageEntity(1L, LanguageEnum.JAVA);

        when(mockedLanguageRepository
                .findByName(LanguageEnum.JAVA))
                .thenReturn(Optional.of(testLanguage));

        UserEntity testLoggedUser = createTestUser();

        testProblem.setLanguage(testLanguage);
        testProblem.setAuthor(testLoggedUser);

        when(mockedLoggedUserHelper.get())
                .thenReturn(testLoggedUser);

        when(mockedUserRepository.save(testLoggedUser))
                .thenReturn(testLoggedUser);

        toTest.addProblem(testAddProblemBindingModel);

        verify(mockedProblemRepository, times(1)).save(problemCaptor.capture());

        verify(mockedUserRepository, times(1)).save(userCaptor.capture());

        ProblemEntity actualProblemEntity = problemCaptor.getValue();
        UserEntity actualUserEntity = userCaptor.getValue();

        Assertions.assertEquals(testProblem.getId(), actualProblemEntity.getId());
        Assertions.assertEquals(testProblem.getTitle(), actualProblemEntity.getTitle());
        Assertions.assertEquals(testProblem.getDescription(), actualProblemEntity.getDescription());
        Assertions.assertEquals(testProblem.getAuthor(), actualProblemEntity.getAuthor());
        Assertions.assertEquals(testProblem.getLanguage(), actualProblemEntity.getLanguage());
        Assertions.assertEquals(testProblem.getComments(), actualProblemEntity.getComments());

        Assertions.assertEquals(testLoggedUser.getId(), actualUserEntity.getId());
        Assertions.assertEquals(1, actualUserEntity.getAddedProblems().size());
    }

    @Test
    void testAddProblemLanguage_NotFound() {

        AddProblemBindingModel testProblemModel = createAddProblemBindingModel();

        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            toTest.addProblem(testProblemModel);
        });
    }

    @Test
    void testGetProblemsByLanguage() {

        ProblemEntity firstProblem = createProblem();
        ProblemEntity secondProblem = createProblem();

        List<ProblemEntity> testProblems = List.of(firstProblem, secondProblem);

        when(mockedProblemRepository.findProblemsByLanguageName(LanguageEnum.JAVA))
                .thenReturn(testProblems);

        ProblemShortInfoViewModel testFirstShortInfoViewModel = new ProblemShortInfoViewModel();
        testFirstShortInfoViewModel.setTitle(firstProblem.getTitle());
        testFirstShortInfoViewModel.setAuthorUsername(firstProblem.getAuthor().getUsername());
        testFirstShortInfoViewModel.setCreatedOn(firstProblem.getCreatedOn());
        testFirstShortInfoViewModel.setId(firstProblem.getId());

        when(mockedModelMapper.map(firstProblem, ProblemShortInfoViewModel.class))
                .thenReturn(testFirstShortInfoViewModel);

        ProblemShortInfoViewModel testSecondShortInfoViewModel = new ProblemShortInfoViewModel();
        testSecondShortInfoViewModel.setTitle(secondProblem.getTitle());
        testSecondShortInfoViewModel.setAuthorUsername(secondProblem.getAuthor().getUsername());
        testSecondShortInfoViewModel.setCreatedOn(secondProblem.getCreatedOn());
        testSecondShortInfoViewModel.setId(secondProblem.getId());

        when(mockedModelMapper.map(secondProblem, ProblemShortInfoViewModel.class))
                .thenReturn(testSecondShortInfoViewModel);

        List<ProblemShortInfoViewModel> result = toTest.getProblemsByLanguage(LanguageEnum.JAVA);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(testFirstShortInfoViewModel.getTitle(), result.get(0).getTitle());
        Assertions.assertEquals(testFirstShortInfoViewModel.getId(), result.get(0).getId());
        Assertions.assertEquals(testFirstShortInfoViewModel.getAuthorUsername(), result.get(0).getAuthorUsername());
        Assertions.assertEquals(testFirstShortInfoViewModel.getCreatedOn(), result.get(0).getCreatedOn());

        Assertions.assertEquals(testSecondShortInfoViewModel.getTitle(), result.get(1).getTitle());
        Assertions.assertEquals(testSecondShortInfoViewModel.getId(), result.get(1).getId());
        Assertions.assertEquals(testSecondShortInfoViewModel.getAuthorUsername(), result.get(1).getAuthorUsername());
        Assertions.assertEquals(testSecondShortInfoViewModel.getCreatedOn(), result.get(1).getCreatedOn());
    }

    @Test
    void testGetProblemById_Found() {

        ProblemEntity testProblem = createProblem();

        when(mockedProblemRepository.findById(testProblem.getId()))
                .thenReturn(Optional.of(testProblem));

        ProblemDetailsInfoViewModel testProblemDetailsInfoViewModel = new ProblemDetailsInfoViewModel();
        testProblemDetailsInfoViewModel.setId(testProblem.getId());
        testProblemDetailsInfoViewModel.setTitle(testProblem.getTitle());
        testProblemDetailsInfoViewModel.setDescription(testProblem.getDescription());
        testProblemDetailsInfoViewModel.setCreatedOn(testProblem.getCreatedOn());
        testProblemDetailsInfoViewModel.setAuthorUsername(testProblem.getAuthor().getUsername());

        when(mockedModelMapper.map(testProblem, ProblemDetailsInfoViewModel.class))
                .thenReturn(testProblemDetailsInfoViewModel);

        ProblemDetailsInfoViewModel resultProblemDetailsInfo = toTest.getProblemDetails(testProblem.getId());

        Assertions.assertEquals(testProblem.getId(), resultProblemDetailsInfo.getId());
        Assertions.assertEquals(testProblem.getTitle(), resultProblemDetailsInfo.getTitle());
        Assertions.assertEquals(testProblem.getDescription(), resultProblemDetailsInfo.getDescription());
        Assertions.assertEquals(testProblem.getCreatedOn(), resultProblemDetailsInfo.getCreatedOn());
        Assertions.assertEquals(testProblem.getAuthor().getUsername(), resultProblemDetailsInfo.getAuthorUsername());
    }

    @Test
    void testGetProblemById_NotFound() {

        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            toTest.getProblemDetails(150L);
        });
    }

    @Test
    void testDeleteProblemById() {

        Long problemId = 1L;

        toTest.deleteProblemById(problemId);

        verify(mockedProblemRepository, times(1)).deleteById(problemId);
    }

    @Test
    void testGetDailyNotificationProblems() {

        ProblemEntity firstTestProblem = createProblem();
        ProblemEntity secondTestProblem = createProblem();

        List<ProblemEntity> testProblems = List.of(firstTestProblem, secondTestProblem);

        when(mockedProblemRepository.findProblemsByCreatedOnIs(LocalDate.now()))
                .thenReturn(testProblems);

        DailyNotificationProblemViewModel testFirstDailyNotificationProblemViewModel = new DailyNotificationProblemViewModel();
        testFirstDailyNotificationProblemViewModel.setTitle(firstTestProblem.getTitle());
        testFirstDailyNotificationProblemViewModel.setAuthorUsername(firstTestProblem.getAuthor().getUsername());

        DailyNotificationProblemViewModel testSecondDailyNotificationProblemViewModel = new DailyNotificationProblemViewModel();
        testSecondDailyNotificationProblemViewModel.setTitle(firstTestProblem.getTitle());
        testSecondDailyNotificationProblemViewModel.setAuthorUsername(firstTestProblem.getAuthor().getUsername());

        when(mockedModelMapper.map(firstTestProblem, DailyNotificationProblemViewModel.class))
                .thenReturn(testFirstDailyNotificationProblemViewModel);

        when(mockedModelMapper.map(secondTestProblem, DailyNotificationProblemViewModel.class))
                .thenReturn(testSecondDailyNotificationProblemViewModel);

        List<DailyNotificationProblemViewModel> result = toTest.getDailyNotificationProblems();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(testFirstDailyNotificationProblemViewModel.getTitle(), result.get(0).getTitle());
        Assertions.assertEquals(testFirstDailyNotificationProblemViewModel.getAuthorUsername(), result.get(0).getAuthorUsername());

        Assertions.assertEquals(testSecondDailyNotificationProblemViewModel.getTitle(), result.get(1).getTitle());
        Assertions.assertEquals(testSecondDailyNotificationProblemViewModel.getAuthorUsername(), result.get(1).getAuthorUsername());
    }

    @Test
    void testDeleteProblemsCreatedBeforeCurrentYear() {

        ProblemEntity firstTestProblem = createProblem();
        ProblemEntity secondTestProblem = createProblem();

        List<ProblemEntity> testProblems = List.of(firstTestProblem, secondTestProblem);

        when(mockedProblemRepository.findProblemsByCreatedOnIsBefore(LocalDate
                .parse(LocalDate.now().getYear() + "-01" + "-01")))
                .thenReturn(testProblems);

        toTest.deleteProblemsCreatedBeforeCurrentYear();

        verify(mockedProblemRepository, times(1)).deleteAll(testProblems);
    }

    private static AddProblemBindingModel createAddProblemBindingModel() {

        AddProblemBindingModel addProblemBindingModel = new AddProblemBindingModel();

        addProblemBindingModel.setTitle("test123");
        addProblemBindingModel.setDescription("test123 test123 test123");
        addProblemBindingModel.setCreatedOn(LocalDate.now());
        addProblemBindingModel.setLanguage(LanguageEnum.JAVA);

        return addProblemBindingModel;
    }

    private static ProblemEntity createProblem() {

        ProblemEntity testProblem = new ProblemEntity();
        UserEntity testUser = createTestUser();

        testProblem.setTitle("test123");
        testProblem.setDescription("test123 test123 test123");
        testProblem.setCreatedOn(LocalDate.now());
        testProblem.setAuthor(testUser);

        return testProblem;
    }

    private static UserEntity createTestUser() {

        UserEntity testUser = new UserEntity();

        testUser.setUsername("USERNAME");
        testUser.setEmail("test@test.com");
        testUser.setPassword("PASSWORD");
        testUser.setAddedProblems(new HashSet<>());
        testUser.setAddedComments(new HashSet<>());

        return testUser;
    }

}