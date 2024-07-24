package com.debugArena.web;

import com.debugArena.model.dto.binding.UserRegisterBindingModel;
import com.debugArena.model.entity.UserEntity;
import com.debugArena.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserRegisterControllerIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "viewUser", authorities = { "view" })
    void testRedirectToHomeWhenUserIsLoggedIn() throws Exception {

        mockMvc.perform(get("/users/register")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        mockMvc.perform(post("/users/register")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    void testUserRegisterWithValidData() throws Exception {

        mockMvc.perform(post("/users/register")
                        .with(csrf())
                        .param("username", "testUser")
                        .param("email", "testEmail@test.com")
                        .param("password", "testPassword")
                        .param("confirmPassword", "testPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));

        Optional<UserEntity> userOpt = userRepository.findByEmail("testEmail@test.com");

        Assertions.assertTrue(userOpt.isPresent());
        Assertions.assertEquals(userOpt.get().getUsername(), "testUser");
        Assertions.assertEquals(userOpt.get().getEmail(), "testEmail@test.com");
    }

    @Test
    void testUserRegisterWithInvalidData() throws Exception {

        mockMvc.perform(post("/users/register")
                        .with(csrf())
                        .param("username", "")
                        .param("email", "invalidEmail")
                        .param("password", "")
                        .param("confirmPassword", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/register"));

        Optional<UserEntity> userOpt = userRepository.findByEmail("invalidEmail");

        Assertions.assertFalse(userOpt.isPresent());
    }


    @Test
    void testRegisterView() throws Exception {

        mockMvc.perform(get("/users/register")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("userRegisterBindingModel"))
                .andExpect(view().name("register"));
    }

}