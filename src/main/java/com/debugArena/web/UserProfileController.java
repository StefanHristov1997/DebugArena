package com.debugArena.web;

import com.debugArena.model.dto.binding.UserProfileBindingModel;
import com.debugArena.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserProfileController {

    private final UserService userService;

    @Autowired
    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String viewProfile(Model model) {

        model.addAttribute("userProfile", userService.getUserProfile());

        return "profile";
    }

    @PatchMapping("/profile/edit-profile")
    public String doEditProfile(
            UserProfileBindingModel userProfileBindingModel) {

        userService.editProfile(userProfileBindingModel);

        return "redirect:/users/profile";
    }

}
