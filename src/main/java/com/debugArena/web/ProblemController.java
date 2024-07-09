package com.debugArena.web;

import com.debugArena.model.dto.binding.AddCommentBindingModel;
import com.debugArena.model.dto.binding.AddProblemBindingModel;
import com.debugArena.model.dto.view.CommentViewModel;
import com.debugArena.model.dto.view.ProblemDetailsInfoViewModel;
import com.debugArena.model.dto.view.ProblemShortInfoViewModel;
import com.debugArena.model.enums.LanguageEnum;
import com.debugArena.service.CommentService;
import com.debugArena.service.ProblemService;
import com.debugArena.service.helpers.LoggedUserHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;
    private final CommentService commentService;

    private final LoggedUserHelper loggedUserHelper;

    @Value("${binding-result-package}")
    private String bindingResultPath;
    private static final String DOT = ".";
    final String attributeName = "addProblemBindingModel";

    @Autowired
    public ProblemController(
            ProblemService problemService,
            CommentService commentService,
            LoggedUserHelper loggedUserHelper) {
        this.problemService = problemService;
        this.commentService = commentService;
        this.loggedUserHelper = loggedUserHelper;
    }

    @ModelAttribute("isAdmin")
    boolean isAdmin() {
        return loggedUserHelper.isAdmin();
    }

    @GetMapping
    public String viewProblemCategories() {
        return "problem-categories";
    }

    @GetMapping("/details/{id}")
    public String viewProblemDetails(@PathVariable("id") Long id, Model model) {

        if (!model.containsAttribute("addCommentBindingModel")) {
            model.addAttribute("addCommentBindingModel", new AddCommentBindingModel());
        }


        ProblemDetailsInfoViewModel problemDetails = problemService.getProblemDetails(id);
        List<CommentViewModel> commentsByProblem = commentService.getCommentsByProblemOrderByRatingDesc(id);

        model.addAttribute("problem", problemDetails);
        model.addAttribute("comments", commentsByProblem);
        model.addAttribute("currentUserEmail", loggedUserHelper.getEmail());

        return "problem-details";
    }

    @GetMapping("/java")
    public String viewProblemsWithJava(Model model) {

        List<ProblemShortInfoViewModel> javaProblems = problemService.getArticlesByLanguage(LanguageEnum.JAVA);

        model.addAttribute("javaProblems", javaProblems);

        return "java-problems";
    }

    @GetMapping("/csharp")
    public String viewProblemsWithCsharp(Model model) {

        List<ProblemShortInfoViewModel> csharpProblems = problemService.getArticlesByLanguage(LanguageEnum.CSHARP);

        model.addAttribute("csharpProblems", csharpProblems);

        return "csharp-problems";
    }

    @GetMapping("/javascript")
    public String viewProblemsWithJavaScript(Model model) {

        List<ProblemShortInfoViewModel> javaScriptProblems = problemService.getArticlesByLanguage(LanguageEnum.JAVASCRIPT);

        model.addAttribute("javaScriptProblems", javaScriptProblems);

        return "javascript-problems";
    }

    @GetMapping("/python")
    public String viewProblemsWithPython(Model model) {

        List<ProblemShortInfoViewModel> pythonProblems = problemService.getArticlesByLanguage(LanguageEnum.PYTHON);

        model.addAttribute("pythonProblems", pythonProblems);

        return "python-problems";
    }

    @GetMapping("/add-problem")
    public String viewAddProblem(Model model) {

        if (!model.containsAttribute(attributeName)) {
            model.addAttribute(attributeName, new AddProblemBindingModel());
        }

        return "add-problem";
    }

    @PostMapping("/add-problem")
    public String doAddProblem(
            @Valid AddProblemBindingModel addProblemBindingModel,
            BindingResult bindingResult,
            RedirectAttributes rAtt
    ) {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute(attributeName, addProblemBindingModel);
            rAtt.addFlashAttribute(bindingResultPath + DOT + attributeName, bindingResult);
            return "redirect:/problems/add-problem";
        }

        problemService.addProblem(addProblemBindingModel);

        return "redirect:/problems";
    }

    @DeleteMapping("/java/delete-problem/{id}")
    public String doDeleteJavaProblemById(
            @PathVariable("id") Long problemId) {

        problemService.deleteProblemById(problemId);

        return "redirect:/problems/java";
    }

    @DeleteMapping("/csharp/delete-problem/{id}")
    public String doDeleteCsharpProblemById(
            @PathVariable("id") Long problemId) {

        problemService.deleteProblemById(problemId);

        return "redirect:/problems/csharp";
    }

    @DeleteMapping("/javascript/delete-problem/{id}")
    public String doDeleteJavaScriptProblemById(
            @PathVariable("id") Long problemId) {

        problemService.deleteProblemById(problemId);

        return "redirect:/problems/javascript";
    }

    @DeleteMapping("/python/delete-problem/{id}")
    public String doDeletePythonProblemById(
            @PathVariable("id") Long problemId) {

        problemService.deleteProblemById(problemId);

        return "redirect:/problems/python";
    }
}
