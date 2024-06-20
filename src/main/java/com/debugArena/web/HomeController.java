package com.debugArena.web;

import com.debugArena.service.helpers.LoggedUserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final LoggedUserHelper loggedUserHelper;

    @Autowired
    public HomeController(LoggedUserHelper loggedUserHelper) {
        this.loggedUserHelper = loggedUserHelper;
    }

    @GetMapping("/")
    public String viewIndex() {

        if (loggedUserHelper.isLogged()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String viewHome() {
        return "home";
    }
}
