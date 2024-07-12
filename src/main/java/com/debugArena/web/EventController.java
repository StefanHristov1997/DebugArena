package com.debugArena.web;

import com.debugArena.model.dto.view.EventShortInfoViewModel;
import com.debugArena.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/add-event")
    public String viewAddEvent() {

        return "add-event";
    }


    @GetMapping
    public String viewAllEvents(Model model) {

        List<EventShortInfoViewModel> events = eventService.getEvents();

        model.addAttribute("events", events);

        return "view-all-events";
    }

}
