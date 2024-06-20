package com.debugArena.web;

import com.debugArena.model.dto.binding.UserResetPasswordBindingModel;
import com.debugArena.service.UserService;
import com.debugArena.service.helpers.LoggedUserHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserResetPasswordController {

    private final UserService userService;
    private final LoggedUserHelper loggedUserHelper;

    @Value("${binding-result-package}")
    private String bindingResultPath;
    private static final String DOT = ".";

    public UserResetPasswordController(UserService userService, LoggedUserHelper loggedUserHelper) {
        this.userService = userService;
        this.loggedUserHelper = loggedUserHelper;
    }

    @GetMapping("reset-password")
    public String resetPassword(Model model) {

        if (loggedUserHelper.isLogged()){
            return "redirect:/home";
        }

        if (!model.containsAttribute("userResetPasswordBindingModel")) {
            model.addAttribute("userResetPasswordBindingModel", new UserResetPasswordBindingModel());
        }

        return "reset-password";
    }

    @PostMapping("reset-password")
    public String doResetPassword(@Valid UserResetPasswordBindingModel userResetPasswordBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes rAtt) {

        if (loggedUserHelper.isLogged()){
            return "redirect:/home";
        }

        final String attribute = "userResetPasswordBindingModel";

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute(attribute, userResetPasswordBindingModel);
            rAtt.addFlashAttribute(bindingResultPath + DOT + attribute, bindingResult);
            return "redirect:/users/reset-password";

        }

        this.userService.resetPassword(userResetPasswordBindingModel);
        return "redirect:/users/login";

    }
}
