package com.debugArena.web;

import com.debugArena.model.dto.binding.UserRegisterBindingModel;
import com.debugArena.service.UserService;
import com.debugArena.service.helpers.LoggedUserHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserRegisterController {

    private final UserService userService;
    private final LoggedUserHelper loggedUserHelper;

    @Value("${binding-result-package}")
    private String bindingResultPath;
    private static final String DOT = ".";

    @Autowired
    public UserRegisterController(UserService userService, LoggedUserHelper loggedUserHelper) {
        this.userService = userService;
        this.loggedUserHelper = loggedUserHelper;
    }

    @GetMapping("/register")
    public String register(Model model) {

        if (loggedUserHelper.isLogged()){
            return "redirect:/home";
        }

        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }

        return "register";
    }


    @PostMapping("/register")
    public String doRegister(@Valid UserRegisterBindingModel userRegisterBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes rAtt) {

        if (loggedUserHelper.isLogged()) {
            return "redirect:/home";
        }

        final String attributeName = "userRegisterBindingModel";

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute(attributeName, userRegisterBindingModel);
            rAtt.addFlashAttribute(bindingResultPath + DOT + attributeName, bindingResult);
            return "redirect:/users/register";
        }

        this.userService.registerUser(userRegisterBindingModel);
        return "redirect:/home";

    }
}
