package com.debugArena.web;

import com.debugArena.model.entity.dto.binding.UserRegisterBindingModel;
import com.debugArena.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserRegisterController {

    private final UserService userService;

    @Value("${binding-result-package}")
    private String bindingResultPath;
    private static final String DOT = ".";

    @Autowired
    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView register(Model model) {

        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }

        return new ModelAndView("register");
    }


    @PostMapping("/register")
    public ModelAndView register
            (@Valid UserRegisterBindingModel userRegisterBindingModel,
             BindingResult bindingResult,
             RedirectAttributes rAtt) {

        final String attributeName = "userRegisterBindingModel";

        final ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute(attributeName, userRegisterBindingModel);
            rAtt.addFlashAttribute(bindingResultPath + DOT + bindingResult);
            modelAndView.setViewName("redirect:register");
        } else {
            this.userService.registerUser(userRegisterBindingModel);
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

}
