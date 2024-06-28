package com.debugArena.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/problems")
public class ProblemController {

    @GetMapping("/add-problem")
    public String viewAddProblem() {
        return "add-problem";
    }
}
