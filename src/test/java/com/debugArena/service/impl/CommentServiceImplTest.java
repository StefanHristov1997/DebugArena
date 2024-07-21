package com.debugArena.service.impl;

import com.debugArena.exeption.ObjectNotFoundException;
import com.debugArena.model.dto.binding.AddCommentBindingModel;
import com.debugArena.model.dto.view.CommentViewModel;
import com.debugArena.model.entity.CommentEntity;
import com.debugArena.model.entity.ProblemEntity;
import com.debugArena.model.entity.UserEntity;
import com.debugArena.repository.CommentRepository;
import com.debugArena.repository.ProblemRepository;
import com.debugArena.service.CommentService;
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
class CommentServiceImplTest {

    private CommentService toTest;

    @Captor
    private ArgumentCaptor<CommentEntity> commentCaptor;

    @Mock
    private CommentRepository mockedCommentRepository;

    @Mock
    private ProblemRepository mockedProblemRepository;

    @Mock
    private LoggedUserHelper mockedLoggedUserHelper;

    @Mock
    private ModelMapper mockedModelMapper;

    @BeforeEach
    void setUp() {
        this.toTest = new CommentServiceImpl(
                mockedCommentRepository,
                mockedProblemRepository,
                mockedLoggedUserHelper,
                mockedModelMapper);
    }

    @Test
    void testAddCommentProblem_Found() {

        CommentEntity testComment = createTestComment();
        AddCommentBindingModel testAddCommentModel = new AddCommentBindingModel();

        testAddCommentModel.setCreatedOn(testComment.getCreatedOn());
        testAddCommentModel.setTextContent(testComment.getTextContent());

        when(mockedModelMapper.map(testAddCommentModel, CommentEntity.class))
                .thenReturn(testComment);

        UserEntity testUser = createTestUser();

        when(mockedLoggedUserHelper.get())
                .thenReturn(testUser);

        testComment.setAuthor(testUser);
        testUser.getAddedComments().add(testComment);

        ProblemEntity testProblem = createTestProblem();

        when(mockedProblemRepository.findById(testProblem.getId()))
                .thenReturn(Optional.of(testProblem));

        testComment.setProblem(testProblem);

        toTest.addComment(testAddCommentModel, testProblem.getId());

        verify(mockedCommentRepository).save(commentCaptor.capture());

        CommentEntity result = commentCaptor.getValue();


        Assertions.assertEquals(testComment.getId(), result.getId());
        Assertions.assertEquals(testComment.getProblem(), result.getProblem());
        Assertions.assertEquals(testComment.getTextContent(), result.getTextContent());
        Assertions.assertEquals(testComment.getAuthor(), result.getAuthor());
    }

    @Test
    void testAddCommentProblem_NotFound() {

        CommentEntity testComment = createTestComment();
        AddCommentBindingModel testAddCommentModel = new AddCommentBindingModel();

        testAddCommentModel.setCreatedOn(testComment.getCreatedOn());
        testAddCommentModel.setTextContent(testComment.getTextContent());

        when(mockedModelMapper.map(testAddCommentModel, CommentEntity.class))
                .thenReturn(testComment);

        UserEntity testUser = createTestUser();

        when(mockedLoggedUserHelper.get())
                .thenReturn(testUser);

        testComment.setAuthor(testUser);
        testUser.getAddedComments().add(testComment);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            toTest.addComment(testAddCommentModel, 150L);
        });
    }

    @Test
    void testUpdateCommentRatingComment_Found() {

        CommentEntity testComment = createTestComment();

        when(mockedCommentRepository.findById(testComment.getId()))
                .thenReturn(Optional.of(testComment));

        testComment.setRating(4);

        toTest.updateCommentRating(testComment.getId(), testComment.getRating());

        verify(mockedCommentRepository).save(commentCaptor.capture());

        CommentEntity result = commentCaptor.getValue();

        Assertions.assertEquals(testComment.getRating(), result.getRating());
    }

    @Test
    void testUpdateCommentRatingComment_NotFound() {

        Assertions
                .assertThrows(ObjectNotFoundException.class, () -> {
                    toTest.updateCommentRating(-1L, 0);
                });
    }

    @Test
    void testDeleteCommentById() {

        Long commentId = 1L;

        toTest.deleteCommentById(commentId);

        verify(mockedCommentRepository, times(1))
                .deleteById(commentId);
    }

    @Test
    void testGetCommentsByProblemOrderByRatingDesc() {

        CommentEntity firstTestComment = createTestComment();
        firstTestComment.setRating(5);

        CommentEntity secondTestComment = createTestComment();
        secondTestComment.setRating(3);

        CommentEntity thirdTestComment = createTestComment();
        thirdTestComment.setRating(4);

        List<CommentEntity> testComments = List.of(firstTestComment, secondTestComment, thirdTestComment);

        when(mockedCommentRepository.findAllByProblemIdOrderByRatingDesc(1L))
                .thenReturn(testComments);

        CommentViewModel firstTestCommentViewModel = new CommentViewModel();
        firstTestCommentViewModel.setId(firstTestComment.getId());
        firstTestCommentViewModel.setRating(firstTestComment.getRating());
        firstTestCommentViewModel.setAuthorUsername(firstTestComment.getAuthor().getUsername());
        firstTestCommentViewModel.setTextContent(firstTestComment.getTextContent());
        firstTestCommentViewModel.setAuthorEmail(firstTestComment.getAuthor().getEmail());
        firstTestCommentViewModel.setCreatedOn(firstTestComment.getCreatedOn());

        when(mockedModelMapper.map(firstTestComment, CommentViewModel.class))
                .thenReturn(firstTestCommentViewModel);

        CommentViewModel secondTestCommentViewModel = new CommentViewModel();
        secondTestCommentViewModel.setId(secondTestComment.getId());
        secondTestCommentViewModel.setRating(secondTestComment.getRating());
        secondTestCommentViewModel.setAuthorUsername(secondTestComment.getAuthor().getUsername());
        secondTestCommentViewModel.setTextContent(secondTestComment.getTextContent());
        secondTestCommentViewModel.setAuthorEmail(secondTestComment.getAuthor().getEmail());
        secondTestCommentViewModel.setCreatedOn(secondTestComment.getCreatedOn());

        when(mockedModelMapper.map(secondTestComment, CommentViewModel.class))
                .thenReturn(secondTestCommentViewModel);

        CommentViewModel thirdTestCommentViewModel = new CommentViewModel();
        thirdTestCommentViewModel.setId(thirdTestComment.getId());
        thirdTestCommentViewModel.setRating(thirdTestComment.getRating());
        thirdTestCommentViewModel.setAuthorUsername(thirdTestComment.getAuthor().getUsername());
        thirdTestCommentViewModel.setTextContent(thirdTestComment.getTextContent());
        thirdTestCommentViewModel.setAuthorEmail(thirdTestComment.getAuthor().getEmail());
        thirdTestCommentViewModel.setCreatedOn(thirdTestComment.getCreatedOn());

        when(mockedModelMapper.map(thirdTestComment, CommentViewModel.class))
                .thenReturn(thirdTestCommentViewModel);

        List<CommentViewModel> resultList = toTest.getCommentsByProblemOrderByRatingDesc(1L);

        Assertions.assertEquals(3, resultList.size());
        Assertions.assertEquals(firstTestCommentViewModel, resultList.get(0));
        Assertions.assertEquals(thirdTestCommentViewModel, resultList.get(2));
        Assertions.assertEquals(secondTestCommentViewModel, resultList.get(1));
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

    private static ProblemEntity createTestProblem() {

        ProblemEntity testProblem = new ProblemEntity();
        UserEntity testUser = createTestUser();

        testProblem.setTitle("test123");
        testProblem.setDescription("test123 test123 test123");
        testProblem.setCreatedOn(LocalDate.now());
        testProblem.setAuthor(testUser);

        return testProblem;
    }

    private static CommentEntity createTestComment() {

        CommentEntity testComment = new CommentEntity();
        UserEntity testUser = createTestUser();
        ProblemEntity testProblem = createTestProblem();

        testComment.setCreatedOn(LocalDate.now());
        testComment.setAuthor(testUser);
        testComment.setProblem(testProblem);
        testComment.setTextContent("This is a test");

        return testComment;
    }
}