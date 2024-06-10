package com.debugArena.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AboutUsController {

    @GetMapping("about-us")
    public ModelAndView aboutUs() {
        return new ModelAndView("about");
    }
}
