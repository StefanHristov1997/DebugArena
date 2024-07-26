package com.debugArena.web;

import com.debugArena.model.dto.binding.UserEditUsernameBindingModel;
import com.debugArena.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserSettingsController {

    private final UserService userService;

    @Value("${binding-result-package}")
    private String bindingResultPath;
    private static final String DOT = ".";
    final String attributeUsername = "userEditUsernameBindingModel";

    private boolean success = false;

    @Autowired
    public UserSettingsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/settings")
    public String viewUserSettings(Model model) {

        if (!model.containsAttribute(attributeUsername)) {
            model.addAttribute(attributeUsername, new UserEditUsernameBindingModel());
        }

        model.addAttribute("successfully", success);
        return "user-settings";
    }

    @PatchMapping("/settings/edit-username")
    public String updateUserPersonalInfo(
            @Valid UserEditUsernameBindingModel userEditUsernameBindingModel,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute(attributeUsername, userEditUsernameBindingModel);
            rAtt.addFlashAttribute(bindingResultPath + DOT + attributeUsername, bindingResult);
            return "redirect:/users/settings";
        }

        userService.editUserUsername(userEditUsernameBindingModel);
        success = true;
        return "redirect:/users/settings";
    }
}
