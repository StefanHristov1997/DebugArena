package com.debugArena.web;

import com.debugArena.model.dto.binding.UserDescriptionBindingModel;
import com.debugArena.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String viewProfile() {
        return "profile";
    }

    @PostMapping("/profile/edit-description")
    public String doEditDescription(
            @Valid UserDescriptionBindingModel userDescriptionBindingModel
            ) {

        userService.editUserDescription(userDescriptionBindingModel);
        return "redirect:/users/profile";
    }
}
