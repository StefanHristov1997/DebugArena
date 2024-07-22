package com.debugArena.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@AutoConfigureMockMvc
class AboutUsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAboutUsView() throws Exception {

        mockMvc.perform(get("/about-us"))
                .andExpect(view().name("about-us"));
    }
}