package com.debugArena.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/events")
public class EventController {


    @GetMapping("/add-event")
    public String viewAddEvent() {

        return "add-event";
    }

}
