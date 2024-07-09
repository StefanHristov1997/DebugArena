package com.debugArena.web;

import com.debugArena.model.dto.binding.EmailSenderBindingModel;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/contact-us")
public class ContactUsController {

    private final UserService userService;

    @Value("${binding-result-package}")
    private String bindingResultPath;
    private static final String DOT = ".";
    final String attributeName = "emailSenderBindingModel";

    @Autowired
    public ContactUsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/successfully-contact-message")
    public String viewSuccessfullySentMessage() {
        return "/messages/successfully-contact-message";
    }

    @GetMapping
    public String viewContactUs(Model model) {

        if (!model.containsAttribute(attributeName)) {
            model.addAttribute(attributeName, new EmailSenderBindingModel());
        }
        return "contact-us";
    }

    @PostMapping
    public String doContactUs(
            @Valid EmailSenderBindingModel emailSenderBindingModel,
            BindingResult bindingResult,
            RedirectAttributes rAtt)
    {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute(attributeName, emailSenderBindingModel);
            rAtt.addFlashAttribute(bindingResultPath + DOT + attributeName, bindingResult);
            return "redirect:/contact-us";
        }

        userService.contactUs(emailSenderBindingModel);

        return "redirect:/contact-us/successfully-contact-message";
    }
}
