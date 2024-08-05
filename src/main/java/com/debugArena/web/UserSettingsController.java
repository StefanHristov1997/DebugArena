package com.debugArena.web;

import com.debugArena.exeption.FuncErrorException;
import com.debugArena.model.dto.binding.UserEditPasswordBindingModel;
import com.debugArena.model.dto.binding.UserEditProfileImageBindingModel;
import com.debugArena.model.dto.binding.UserEditUsernameBindingModel;
import com.debugArena.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserSettingsController {

    private final UserService userService;

    @Value("${binding-result-package}")
    private String bindingResultPath;
    private static final String DOT = ".";
    final String attributeUsername = "userEditUsernameBindingModel";
    final String attributePassword = "userEditPasswordBindingModel";
    final String attributeProfileImage = "userEditProfileImageBindingModel";

    private boolean successfullyEditUsername = false;
    private boolean successfullyEditPassword = false;

    @Autowired
    public UserSettingsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/settings")
    public String viewUserSettings(Model model) {

        if (!model.containsAttribute(attributeUsername)) {
            model.addAttribute(attributeUsername, new UserEditUsernameBindingModel());
        }

        if (!model.containsAttribute(attributePassword)) {
            model.addAttribute(attributePassword, new UserEditPasswordBindingModel());
        }

        if (!model.containsAttribute(attributeProfileImage)) {
            model.addAttribute(attributeProfileImage, new UserEditProfileImageBindingModel());
        }

        model.addAttribute("successfullyEditUsername", successfullyEditUsername);
        model.addAttribute("successfullyEditPassword", successfullyEditPassword);

        successfullyEditPassword = false;
        successfullyEditUsername = false;
        return "user-settings";
    }

    @PatchMapping("/settings/edit-username")
    public String updateUserUsername(
            @Valid UserEditUsernameBindingModel userEditUsernameBindingModel,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute(attributeUsername, userEditUsernameBindingModel);
            rAtt.addFlashAttribute(bindingResultPath + DOT + attributeUsername, bindingResult);
            return "redirect:/users/settings";
        }

        userService.editUserUsername(userEditUsernameBindingModel);
        successfullyEditUsername = true;
        return "redirect:/users/settings";
    }

    @PatchMapping("/settings/edit-password")
    public String updateUserPassword(
            @Valid UserEditPasswordBindingModel userEditPasswordBindingModel,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute(attributePassword, userEditPasswordBindingModel);
            rAtt.addFlashAttribute(bindingResultPath + DOT + attributePassword, bindingResult);
            return "redirect:/users/settings";
        }

        userService.editUserPassword(userEditPasswordBindingModel);
        successfullyEditPassword = true;
        return "redirect:/users/settings";
    }

    @PostMapping("/settings/upload-image")
    public String uploadImage(@Valid UserEditProfileImageBindingModel userEditProfileImageBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute(attributeProfileImage, userEditProfileImageBindingModel);
            rAtt.addFlashAttribute(bindingResultPath + DOT + attributeProfileImage, bindingResult);
            return "redirect:/users/settings";
        }

        userService.uploadProfileImage(userEditProfileImageBindingModel);

        return "redirect:/";
    }

    @ExceptionHandler(FuncErrorException.class)
    public String handleImageError(RedirectAttributes rAtt) {
        rAtt.addFlashAttribute("error", true);
        return "redirect:/users/settings";
    }
}
