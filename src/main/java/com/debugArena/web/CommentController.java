package com.debugArena.web;

import com.debugArena.model.dto.binding.AddCommentBindingModel;
import com.debugArena.service.CommentService;
import com.debugArena.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @Value("${binding-result-package}")
    private String bindingResultPath;
    private static final String DOT = ".";
    final String attributeName = "addCommentBindingModel";

    public CommentController(
            CommentService commentService,
            UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping("/add-comment/{id}")
    public String doAddComment(
            @PathVariable("id") Long problemId,
            @Valid AddCommentBindingModel addCommentBindingModel,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute(attributeName, addCommentBindingModel);
            rAtt.addFlashAttribute(bindingResultPath + DOT + attributeName, bindingResult);

            return "redirect:/problems/details/" + problemId;
        }

        commentService.addComment(addCommentBindingModel, problemId);

        return "redirect:/problems/details/" + problemId;
    }

    @PatchMapping("/rating/{commentId}/{problemId}")
    public String updateCommentRating(
            @PathVariable(value = "commentId") Long commentId,
            @PathVariable(value = "problemId") Long problemId,
            @RequestParam(value = "rating") int rating) {

        commentService.updateCommentRating(commentId, rating);

        return "redirect:/problems/details/" + problemId;
    }

    @DeleteMapping("/delete-comment/{commentId}/{problemId}")
    public String deleteComment(
            RedirectAttributes rAtt,
            @PathVariable(value = "commentId") Long commentId,
            @PathVariable(value = "problemId") Long problemId
    ) {

//        boolean canUserDeleteComment = commentService.canUserDeleteComment(problemId);

//        if (canUserDeleteComment) {
//            rAtt.addFlashAttribute("canDeleteComment", true);
            userService.deleteComment(commentId);
//

        return "redirect:/problems/details/" + problemId;
    }

}
