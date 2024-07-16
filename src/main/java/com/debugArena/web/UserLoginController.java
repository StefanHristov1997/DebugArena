package com.debugArena.web;

import com.debugArena.service.helpers.LoggedUserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserLoginController {

    private final LoggedUserHelper loggedUserHelper;

    @Autowired
    public UserLoginController(LoggedUserHelper loggedUserHelper) {
        this.loggedUserHelper = loggedUserHelper;
    }

    @GetMapping("/login")
    public String viewLogin() {

        if(loggedUserHelper.isLogged()){
            return "redirect:/home";
        }

        return "login";
    }

    @PostMapping("/login-error")
    public String loginError(Model model) {

        if(loggedUserHelper.isLogged()){
            return "redirect:/home";
        }

        model.addAttribute("loginError", true);

        return "login";
    }
}
