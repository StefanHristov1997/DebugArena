package com.debugArena.service.helpers;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class EventAPIServerStatusHelper {

    private final RestClient restClient;

    public EventAPIServerStatusHelper(RestClient restClient) {
        this.restClient = restClient;
    }


    public boolean isApiServerUp() {
        try {
            String response = restClient
                    .get()
                    .uri("/events/check")
                    .retrieve().body(String.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
