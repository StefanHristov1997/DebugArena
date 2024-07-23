package com.debugArena.web;

import com.debugArena.model.entity.UserEntity;
import com.debugArena.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserLoginControllerIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginView() throws Exception {

        mockMvc.perform(get("/users/login"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login"));
    }

    @Test
    @WithMockUser(username = "viewUser", authorities = {"view"})
    void testRedirectWhenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/users/login")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));

    }

    @Test
    void testLoginError() throws Exception {

        mockMvc.perform(post("/users/login-error")
                .with(csrf())
                .param("email", "invalidEmail")
                .param("password", "invalidPassword"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("loginError"))
                .andExpect(view().name("login"));
    }
}