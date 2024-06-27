package com.debugArena.web;

import com.debugArena.service.helpers.LoggedUserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserProfileController {

    private final LoggedUserHelper loggedUserHelper;

    @Autowired
    public UserProfileController(LoggedUserHelper loggedUserHelper) {
        this.loggedUserHelper = loggedUserHelper;
    }

    @GetMapping("/profile")
    public String viewProfile() {

        return "profile";
    }
}
