package com.debugArena.web;

import com.debugArena.model.dto.binding.UserProfileBindingModel;
import com.debugArena.model.entity.UserEntity;
import com.debugArena.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
}