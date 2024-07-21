package com.debugArena.service.impl;

import com.debugArena.exeption.ObjectNotFoundException;
import com.debugArena.model.dto.binding.AddCommentBindingModel;
import com.debugArena.model.dto.binding.AddProblemBindingModel;
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
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        ProblemEntity testProblem = createProblem();

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

    private static UserEntity createTestUser() {

        UserEntity testUser = new UserEntity();

        testUser.setUsername("USERNAME");
        testUser.setEmail("test@test.com");
        testUser.setPassword("PASSWORD");
        testUser.setAddedProblems(new HashSet<>());
        testUser.setAddedComments(new HashSet<>());

        return testUser;
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

    private static CommentEntity createTestComment() {

        CommentEntity testComment = new CommentEntity();
        UserEntity testUser = createTestUser();
        ProblemEntity testProblem = createProblem();

        testComment.setCreatedOn(LocalDate.now());
        testComment.setAuthor(testUser);
        testComment.setProblem(testProblem);
        testComment.setTextContent("This is a test");

        return testComment;
    }
}