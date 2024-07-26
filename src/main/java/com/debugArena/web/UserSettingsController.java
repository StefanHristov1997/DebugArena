package com.debugArena.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserSettingsController {

    @GetMapping("/settings")
    public String viewUserSettings() {
        return "user-settings";
    }
}
