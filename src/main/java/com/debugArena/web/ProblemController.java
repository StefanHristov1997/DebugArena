package com.debugArena.web;

import com.debugArena.model.dto.binding.AddProblemBindingModel;
import com.debugArena.model.dto.view.ArticleViewModel;
import com.debugArena.model.enums.LanguageEnum;
import com.debugArena.service.ProblemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;

    @Value("${binding-result-package}")
    private String bindingResultPath;
    private static final String DOT = ".";
    final String attributeName = "addProblemBindingModel";

    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @GetMapping
    public String viewProblemCategories() {
        return "problem-categories";
    }

    @GetMapping("/java")
    public String viewProblemsWithJava(Model model) {

        List<ArticleViewModel> javaArticles = problemService.getArticlesByLanguage(LanguageEnum.JAVA);

        model.addAttribute("javaArticles", javaArticles);

        return "java-articles";
    }

    @GetMapping("/csharp")
    public String viewProblemsWithCsharp(Model model) {

        List<ArticleViewModel> csharpArticles = problemService.getArticlesByLanguage(LanguageEnum.CSHARP);

        model.addAttribute("csharpArticles", csharpArticles);

        return "csharp-articles";
    }

    @GetMapping("/javascript")
    public String viewProblemsWithJavaScript(Model model) {

        List<ArticleViewModel> javaScriptArticles = problemService.getArticlesByLanguage(LanguageEnum.JAVASCRIPT);

        model.addAttribute("javaScriptArticles", javaScriptArticles);

        return "javascript-articles";
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
}
