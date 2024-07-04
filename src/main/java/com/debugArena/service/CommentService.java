package com.debugArena.service;

import com.debugArena.model.dto.binding.AddCommentBindingModel;
import com.debugArena.model.dto.view.CommentViewModel;

import java.util.List;

public interface CommentService {

    void addComment(AddCommentBindingModel addCommentBindingModel, Long problemId);

    List<CommentViewModel> getCommentsByProblemOrderByRatingDesc(Long id);

    void updateCommentRating(Long id, int rating);
}
