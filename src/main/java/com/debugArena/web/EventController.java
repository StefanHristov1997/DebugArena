package com.debugArena.web;

import com.debugArena.exeption.ServerConnectionException;
import com.debugArena.exeption.ObjectNotFoundException;
import com.debugArena.model.dto.binding.AddEventBindingModel;
import com.debugArena.model.dto.view.EventDetailsInfoViewModel;
import com.debugArena.model.dto.view.EventShortInfoViewModel;
import com.debugArena.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private EventService eventService;

    @Value("${binding-result-package}")
    private String bindingResultPath;
    private static final String DOT = ".";
    final String attributeName = "addEventBindingModel";

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/add-event")
    public String viewAddEvent(Model model) {

        if (!model.containsAttribute(attributeName)) {
            model.addAttribute(attributeName, new AddEventBindingModel());
        }

        return "add-event";
    }

    @PostMapping("/add-event")
    public String doRegisterEvent(
            @Valid AddEventBindingModel addEventBindingModel,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute(attributeName, addEventBindingModel);
            rAtt.addFlashAttribute(bindingResultPath + DOT + attributeName, bindingResult);
            return "redirect:/events/add-event";
        }

        eventService.registerEvent(addEventBindingModel);
        return "redirect:/events";
    }

    @GetMapping("/details/{id}")
    public String viewEventById(@PathVariable("id") Long id, Model model) {

        EventDetailsInfoViewModel eventDetails = eventService.getEventDetailsInfoById(id);

        model.addAttribute("eventDetails", eventDetails);

        return "event-details";
    }

    @GetMapping
    public String viewAllEvents(Model model) {

        List<EventShortInfoViewModel> events = eventService.getEvents();

        model.addAttribute("events", events);

        return "view-all-events";
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public String handleEventNotFound() {
        return "/error/object-not-found";
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServerConnectionException.class)
    public String handleServerConnectionException() {
        return "/error/server-connection-error";
    }

}
