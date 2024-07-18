package com.debugArena.service.impl;

import com.debugArena.exeption.ServerConnectionException;
import com.debugArena.exeption.ObjectNotFoundException;
import com.debugArena.model.dto.binding.AddEventBindingModel;
import com.debugArena.model.dto.view.EventDetailsInfoViewModel;
import com.debugArena.model.dto.view.EventShortInfoViewModel;
import com.debugArena.service.EventService;
import com.debugArena.service.helpers.EventAPIServerStatusHelper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final RestClient restClient;
    private final EventAPIServerStatusHelper eventAPIServerStatusHelper;

    public EventServiceImpl(
            RestClient restClient,
            EventAPIServerStatusHelper eventAPIServerStatusHelper) {

        this.restClient = restClient;
        this.eventAPIServerStatusHelper = eventAPIServerStatusHelper;
    }

    @Override
    public void registerEvent(AddEventBindingModel addEventBindingModel) {

        checkingServerStatus();

            restClient
                    .post()
                    .uri("api/events/create")
                    .body(addEventBindingModel)
                    .retrieve();
    }

    @Override
    public EventDetailsInfoViewModel getEventDetailsInfoById(Long id) {

        checkingServerStatus();

        try {
            return restClient
                    .get()
                    .uri("api/events/{id}", id)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(EventDetailsInfoViewModel.class);
        } catch (RestClientException e) {
            throw new ObjectNotFoundException("Event with id " + id + " not found");
        }
    }

    @Override
    public List<EventShortInfoViewModel> getEvents() {

        checkingServerStatus();

            return restClient
                    .get()
                    .uri("api/events/all")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
    }

    @Override
    public List<EventDetailsInfoViewModel> getWeeklyEvents() {

        checkingServerStatus();

        return restClient
                .get()
                .uri("api/events/weekly-events")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }


    private void checkingServerStatus() {
        if (!eventAPIServerStatusHelper.isApiServerUp()){
            throw new ServerConnectionException();
        }
    }
}
