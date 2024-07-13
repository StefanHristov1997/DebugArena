package com.debugArena.service.impl;

import com.debugArena.model.dto.binding.AddEventBindingModel;
import com.debugArena.model.dto.view.EventDetailsInfoViewModel;
import com.debugArena.model.dto.view.EventShortInfoViewModel;
import com.debugArena.service.EventService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private RestClient restClient;

    public EventServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void registerEvent(AddEventBindingModel addEventBindingModel) {
        restClient
                .post()
                .uri("/events")
                .body(addEventBindingModel)
                .retrieve();
    }

    @Override
    public EventDetailsInfoViewModel getEventDetailsInfoById(Long id) {
        return restClient
                .get()
                .uri("/events/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(EventDetailsInfoViewModel.class);
    }

    @Override
    public List<EventShortInfoViewModel> getEvents() {
        return restClient
                .get()
                .uri("/events")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
