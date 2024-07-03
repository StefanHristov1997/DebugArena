package com.debugArena.web;

import com.debugArena.model.dto.binding.AddCommentBindingModel;
import com.debugArena.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add-comment/{id}")
    public String doAddComment(@PathVariable("id") Long problemId, AddCommentBindingModel addCommentBindingModel) {

        commentService.addComment(addCommentBindingModel, problemId);

        return "redirect:/problems/details/" + problemId;
    }
}
