package com.debugArena.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactUsController {

    @GetMapping("/contact-us")
    public ModelAndView contactUs(){
        return new ModelAndView("contact-us");
    }
}
