package com.debugArena.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestConfig {

    @Bean
    public RestClient restClient(EventApiConfig eventApi) {
        return RestClient
                .builder()
                .baseUrl(eventApi.getBaseUrl())
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
