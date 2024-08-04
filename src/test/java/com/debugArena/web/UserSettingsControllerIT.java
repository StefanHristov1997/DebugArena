package com.debugArena.web;

import com.debugArena.model.entity.UserEntity;
import com.debugArena.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserSettingsControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void clearDB() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "viewUser", authorities = {"view"})
    void testViewSettingsPageWithLoggedUser() throws Exception {

        mockMvc.perform(get("/users/settings"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("successfullyEditUsername"))
                .andExpect(model().attributeExists("successfullyEditPassword"))
                .andExpect(view().name("user-settings"));
    }

    @Test
    void testViewSettingsPageWithNoLoggedUser() throws Exception {

        mockMvc.perform(get("/users/settings"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = {"ROLE_ADMIN"})
    void testUpdateUserUsernameWithValidData() throws Exception {

        UserEntity testUser = new UserEntity();
        testUser.setUsername("Shristov 16");
        testUser.setPassword("test123");
        testUser.setEmail("shristov16@e-dnrs.org");

        userRepository.save(testUser);

        mockMvc.perform(patch("/users/settings/edit-username")
                        .with(csrf())
                        .param("username", "newUsername"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/settings"));

        Optional<UserEntity> userOpt = userRepository.findByUsername("newUsername");

        Assertions.assertTrue(userOpt.isPresent());
        Assertions.assertEquals("shristov16@e-dnrs.org", userOpt.get().getEmail());
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = {"ROLE_ADMIN"})
    void testUpdateUserUsernameWithUnValidData() throws Exception {

        UserEntity testUser = new UserEntity();
        testUser.setUsername("Shristov 16");
        testUser.setPassword("test123");
        testUser.setEmail("shristov16@e-dnrs.org");

        userRepository.save(testUser);

        mockMvc.perform(patch("/users/settings/edit-username")
                        .with(csrf())
                        .param("username", " "))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/settings"));

        Optional<UserEntity> userOpt = userRepository.findByUsername("newUsername");

        Assertions.assertFalse(userOpt.isPresent());
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = {"ROLE_ADMIN"})
    void testUpdateUserPasswordWithValidData() throws Exception {

        UserEntity testUser = new UserEntity();
        testUser.setUsername("Shristov 16");
        testUser.setPassword("test123");
        testUser.setEmail("shristov16@e-dnrs.org");

        userRepository.save(testUser);

        mockMvc.perform(patch("/users/settings/edit-password")
                        .with(csrf())
                        .param("password", "newPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/settings"));

        Optional<UserEntity> userOpt = userRepository.findByUsername("Shristov 16");

        Assertions.assertTrue(userOpt.isPresent());
        Assertions.assertTrue(passwordEncoder.matches("newPassword", userOpt.get().getPassword()));
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = {"ROLE_ADMIN"})
    void testUpdateUserPasswordWithUnValidData() throws Exception {

        UserEntity testUser = new UserEntity();
        testUser.setUsername("Shristov 16");
        testUser.setPassword("test123");
        testUser.setEmail("shristov16@e-dnrs.org");

        userRepository.save(testUser);

        mockMvc.perform(patch("/users/settings/edit-password")
                        .with(csrf())
                        .param("password", " "))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/settings"));

        Optional<UserEntity> userOpt = userRepository.findByEmail("shristov16@e-dnrs.org");

        Assertions.assertTrue(userOpt.isPresent());
        Assertions.assertFalse(passwordEncoder.matches(" ", userOpt.get().getPassword()));
    }


}