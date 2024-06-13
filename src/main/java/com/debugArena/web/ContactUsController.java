package com.debugArena.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactUsController {

    @GetMapping("/contact-us")
    public String contactUs() {
        return ("contact-us");
    }
}
