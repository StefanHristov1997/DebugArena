package com.debugArena.service.impl;

import com.debugArena.exeption.ObjectNotFoundException;
import com.debugArena.exeption.ServerConnectionException;
import com.debugArena.model.dto.binding.AddEventBindingModel;
import com.debugArena.model.dto.binding.EventMainBindingModel;
import com.debugArena.model.dto.view.EventDetailsInfoViewModel;
import com.debugArena.model.dto.view.EventShortInfoViewModel;
import com.debugArena.model.entity.UserEntity;
import com.debugArena.repository.UserRepository;
import com.debugArena.service.EventService;
import com.debugArena.service.helpers.EventAPIServerStatusHelper;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final RestClient restClient;
    private final EventAPIServerStatusHelper eventAPIServerStatusHelper;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public EventServiceImpl(
            RestClient restClient,
            EventAPIServerStatusHelper eventAPIServerStatusHelper, UserRepository userRepository, ModelMapper modelMapper) {

        this.restClient = restClient;
        this.eventAPIServerStatusHelper = eventAPIServerStatusHelper;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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
    public EventDetailsInfoViewModel getEventDetailsInfoById(Long eventId) {

        checkingServerStatus();

        try {
            return restClient
                    .get()
                    .uri("api/events/{id}", eventId)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(EventDetailsInfoViewModel.class);
        } catch (RestClientException e) {
            throw new ObjectNotFoundException("Event with eventID " + eventId + " not found");
        }
    }

    @Override
    public void deleteEvent(Long eventId) {

        checkingServerStatus();

        restClient.delete()
                .uri("api/events/delete/{id}", eventId)
                .retrieve();
    }

    @Override
    public List<EventShortInfoViewModel> getEvents() {

        checkingServerStatus();

        List<EventMainBindingModel> response = restClient
                .get()
                .uri("api/events/all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assert response != null;

        return response.stream().peek(e -> {
            UserEntity author = userRepository.findByEmail(e.getAuthorEmail())
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User with email: " + e.getAuthorEmail() + " is not found"));

            e.setAuthorImageURL(author.getImageURL());
        }).map(e -> modelMapper.map(e, EventShortInfoViewModel.class)).toList();
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
        if (!eventAPIServerStatusHelper.isApiServerUp()) {
            throw new ServerConnectionException();
        }
    }
}
