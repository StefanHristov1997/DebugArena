package com.debugArena.web;

import com.debugArena.model.entity.UserEntity;
import com.debugArena.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserProfileControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clearDB() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = {"view"})
    void testProfileView() throws Exception {

        UserEntity testUser = new UserEntity();
        testUser.setUsername("Shristov 16");
        testUser.setPassword("test123");
        testUser.setEmail("shristov16@e-dnrs.org");
        testUser.setSkills("");
        testUser.setInterests("");
        testUser.setDescription("");

        userRepository.save(testUser);

        mockMvc.perform(get("/users/profile")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("userProfile"))
                .andExpect(view().name("profile"));
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = {"view"})
    void testEditProfile() throws Exception {

        UserEntity testUser = new UserEntity();
        testUser.setUsername("Shristov 16");
        testUser.setPassword("test123");
        testUser.setEmail("shristov16@e-dnrs.org");
        testUser.setSkills("");
        testUser.setInterests("");
        testUser.setDescription("");

        userRepository.save(testUser);

        mockMvc.perform(patch("/users/profile/edit-profile")
                        .with(csrf())
                .param("description", "test description123")
                .param("skills", "test skills123")
                .param("interests", "test interests123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/profile"));

        Optional<UserEntity> userOpt = userRepository.findByEmail("shristov16@e-dnrs.org");

        Assertions.assertTrue(userOpt.isPresent());
        Assertions.assertEquals("test description123" , userOpt.get().getDescription());
        Assertions.assertEquals("test skills123" , userOpt.get().getSkills());
        Assertions.assertEquals("test interests123" , userOpt.get().getInterests());
    }
}