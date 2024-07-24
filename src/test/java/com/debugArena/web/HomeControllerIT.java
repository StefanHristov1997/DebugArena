package com.debugArena.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testViewIndexPageWhenUserIsNotLoggedIn() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("index"));
    }

    @Test
    @WithMockUser(username = "viewUser", authorities = {"view"})
    void testRedirectToHomePageWhenUserIsLoggedIn() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    void testRedirectToLoginWhenUserIsNotLoggedIn() throws Exception {

        mockMvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(username = "viewUser", authorities = {"view"})
    void testViewHomePage() throws Exception {

        mockMvc.perform(get("/home"))
                .andExpect(view().name("home"));
    }
}