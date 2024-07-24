package com.debugArena.web;

import com.debugArena.model.entity.ProblemEntity;
import com.debugArena.model.entity.UserEntity;
import com.debugArena.model.enums.LanguageEnum;
import com.debugArena.repository.ProblemRepository;
import com.debugArena.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProblemControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    @WithMockUser(username = "Shristov 16", authorities = {"ROLE_ADMIN"})
    void testModelAttributeIsAdminReturn_True() throws Exception {

        mockMvc.perform(get("/problems"))
                .andExpect(model().attribute("isAdmin", true));
    }

    @Test
    @WithMockUser(username = "Shristov 16", authorities = {"ROLE_USER"})
    void testModelAttributeIsAdminReturn_False() throws Exception {

        mockMvc.perform(get("/problems"))
                .andExpect(model().attribute("isAdmin", false));
    }

    @Test
    void testRedirectToLoginPageWhenUserIsNotLoggedInForProblemsRoute() throws Exception {

        mockMvc.perform(get("/problems"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(username = "Shristov 16", authorities = {"ROLE_USER"})
    void testViewProblemsWhenUserIsLogged() throws Exception {

        mockMvc.perform(get("/problems"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("problem-categories"));
    }

    @Test
    void testRedirectToLoginPageWhenUserIsNotLoggedInForAddProblemRoute() throws Exception {

        mockMvc.perform(get("/problems/add-problem"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(username = "Shristov 16", authorities = {"ROLE_USER"})
    void testViewAddProblemWhenUserIsLogged() throws Exception {

        mockMvc.perform(get("/problems/add-problem"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("addProblemBindingModel"))
                .andExpect(view().name("add-problem"));
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = "ROLE_USER")
    void testAddProblemWithValidaData() throws Exception {

        UserEntity testUser = new UserEntity();
        testUser.setUsername("Shristov1997");
        testUser.setPassword("test123");
        testUser.setEmail("shristov16@e-dnrs.org");

        userRepository.save(testUser);

        mockMvc.perform(post("/problems/add-problem")
                        .with(csrf())
                        .param("id", String.valueOf(1L))
                        .param("title", "Test Title123")
                        .param("description", "Test Description123123")
                        .param("createdOn", String.valueOf(LocalDate.now()))
                        .param("language", String.valueOf(LanguageEnum.JAVA)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/problems"));

        Optional<ProblemEntity> problemOpt = problemRepository.findById(1L);

        Assertions.assertTrue(problemOpt.isPresent());
        Assertions.assertEquals(1L, problemOpt.get().getId());
        Assertions.assertEquals("Test Title123", problemOpt.get().getTitle());
        Assertions.assertEquals("Test Description123123", problemOpt.get().getDescription());
        Assertions.assertEquals(LanguageEnum.JAVA, problemOpt.get().getLanguage().getName());
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.bg", authorities = "ROLE_USER")
    void testAddProblemThrowErrorWithNoValidaData() throws Exception {

        UserEntity testUser = new UserEntity();
        testUser.setUsername("Shristov 16");
        testUser.setPassword("test123");
        testUser.setEmail("shristov16@e-dnrs.bg");

        userRepository.save(testUser);

        Assertions.assertThrows(AssertionError.class, () -> {
            mockMvc.perform(post("/problems/add-problem")
                            .with(csrf())
                            .param("id", String.valueOf(1L))
                            .param("title", "")
                            .param("description", ""))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/problems"));
        });
    }

}