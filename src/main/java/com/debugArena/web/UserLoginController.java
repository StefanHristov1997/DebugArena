package com.debugArena.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserLoginController {

    @GetMapping("/login")
    public String login() {
        return ("login");
    }

    @PostMapping("/login-error")
    public String loginError(Model model) {

        model.addAttribute("loginError", true);

        return ("login");
    }
}
