package com.debugArena.service.impl;

import com.debugArena.model.dto.view.EventShortInfoViewModel;
import com.debugArena.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
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
