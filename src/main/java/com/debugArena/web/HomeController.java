package com.debugArena.web;

import com.debugArena.service.helpers.LoggedUserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class HomeController {

    private final LoggedUserHelper loggedUserHelper;
    private final MessageSource messageSource;

    @Autowired
    public HomeController(LoggedUserHelper loggedUserHelper, MessageSource messageSource) {
        this.loggedUserHelper = loggedUserHelper;
        this.messageSource = messageSource;
    }

    @GetMapping("/")
    public String viewIndex() {

        if (loggedUserHelper.isLogged()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String viewHome(Locale locale) {
        String welcomeMessage = messageSource.getMessage("welcome.message", null, locale);
        System.out.println("msg: " + welcomeMessage);
        return "home";
    }
}
