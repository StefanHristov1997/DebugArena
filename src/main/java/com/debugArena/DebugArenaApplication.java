package com.debugArena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@SpringBootApplication
@EnableSpringConfigured
public class DebugArenaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DebugArenaApplication.class, args);
    }

}
