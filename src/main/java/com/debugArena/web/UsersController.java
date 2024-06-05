package com.debugArena.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UsersController {

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

// TODO: Post Register Method
//    @PostMapping
//    public ModelAndView register() {
//
//    }

}
