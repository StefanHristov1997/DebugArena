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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ProblemRepository problemRepository;

    private final LoggedUserHelper loggedUserHelper;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ProblemRepository problemRepository, LoggedUserHelper loggedUserHelper, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.problemRepository = problemRepository;
        this.loggedUserHelper = loggedUserHelper;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addComment(AddCommentBindingModel addCommentBindingModel, Long problemId) {

        CommentEntity commentToSave = modelMapper.map(addCommentBindingModel, CommentEntity.class);

        UserEntity currentUser = loggedUserHelper.get();
        commentToSave.setAuthor(currentUser);
        currentUser.getAddedComments().add(commentToSave);

        ProblemEntity problem = problemRepository
                .findById(problemId)
                .orElseThrow(() -> new ObjectNotFoundException("Problem wih " + problemId + " is not found!"));

        commentToSave.setProblem(problem);

        commentRepository.save(commentToSave);
    }

    @Override
    public void updateCommentRating(Long id, int rating) {

        CommentEntity comment = commentRepository
                .findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Comment with " + id + " is not found!"));

        comment.setRating(rating);

        commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentViewModel> getCommentsByProblemOrderByRatingDesc(Long id) {

        List<CommentEntity> comments = commentRepository.findAllByProblemIdOrderByRatingDesc(id);

        return comments
                .stream()
                .map(comment ->
                        modelMapper.map(comment, CommentViewModel.class)).toList();
    }
}
