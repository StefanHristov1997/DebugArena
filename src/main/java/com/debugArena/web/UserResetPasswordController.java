package com.debugArena.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserResetPasswordController {

    @GetMapping("reset-password")
    public ModelAndView resetPassword() {
       return new ModelAndView("reset-password");
    }
}
