package com.debugArena.web;

import com.debugArena.model.entity.LanguageEntity;
import com.debugArena.model.entity.ProblemEntity;
import com.debugArena.model.entity.UserEntity;
import com.debugArena.model.enums.LanguageEnum;
import com.debugArena.repository.LanguageRepository;
import com.debugArena.repository.ProblemRepository;
import com.debugArena.repository.UserRepository;
import com.debugArena.service.helpers.LoggedUserHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProblemControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProblemRepository problemRepository;

    @Mock
    private LoggedUserHelper loggedUserHelper;

    @Mock
    private ModelMapper mapper;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private UserRepository userRepository;

    private UserEntity testUser;


    @BeforeEach
    void setUp() {
        this.testUser = new UserEntity();

        testUser.setUsername("Shristov1997");
        testUser.setPassword("test123");
        testUser.setEmail("shristov16@e-dnrs.org");

        userRepository.save(testUser);
    }

    @AfterEach
    void clearDB() {
        problemRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = {"ROLE_ADMIN" })
    void testModelAttributeIsAdminReturn_True() throws Exception {

        mockMvc.perform(get("/problems"))
                .andExpect(model().attribute("isAdmin", true));
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = {"ROLE_USER" })
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
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = {"ROLE_USER" })
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
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = {"ROLE_USER" })
    void testViewAddProblemWhenUserIsLogged() throws Exception {

        mockMvc.perform(get("/problems/add-problem"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("addProblemBindingModel"))
                .andExpect(view().name("add-problem"));
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = "ROLE_USER")
    void testAddProblemWithValidaData() throws Exception {

        mockMvc.perform(post("/problems/add-problem")
                        .with(csrf())
                        .param("id", String.valueOf(2L))
                        .param("title", "Test Title123")
                        .param("description", "Test Description123123")
                        .param("createdOn", String.valueOf(LocalDate.now()))
                        .param("language", String.valueOf(LanguageEnum.JAVA)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/problems"));

        Optional<ProblemEntity> problemOpt = problemRepository.findById(2L);

        when(loggedUserHelper.get())
                .thenReturn(testUser);

        UserEntity user = loggedUserHelper.get();

        Assertions.assertTrue(problemOpt.isPresent());
        Assertions.assertEquals(2L, problemOpt.get().getId());
        Assertions.assertEquals("Test Title123", problemOpt.get().getTitle());
        Assertions.assertEquals("Test Description123123", problemOpt.get().getDescription());
        Assertions.assertEquals(LanguageEnum.JAVA, problemOpt.get().getLanguage().getName());
        Assertions.assertEquals(user.getEmail(), problemOpt.get().getAuthor().getEmail());
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = "ROLE_USER")
    void testAddProblemThrowErrorWithNoValidaData() throws Exception {

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

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = "ROLE_USER")
    void testViewProblemDetails() throws Exception {

        ProblemEntity testProblem = new ProblemEntity();

        testProblem.setTitle("Test Title123123");
        testProblem.setDescription("Test Description123123");
        testProblem.setCreatedOn(LocalDate.now());

        LanguageEntity language = languageRepository.findByName(LanguageEnum.JAVA).get();

        testProblem.setLanguage(language);

        testProblem.setAuthor(testUser);

        problemRepository.save(testProblem);

        mockMvc.perform(get("/problems/details/{id}", testProblem.getId())
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("problem"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(model().attributeExists("currentUserEmail"))
                .andExpect(view().name("problem-details"));
    }

    @Test
    void testRedirectToLoginPageWhenUserIsNotLoggedInForProblemDetailsRoute() throws Exception {

        mockMvc.perform(get("/problems/details/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = "ROLE_USER")
    void testViewErrorPageWhenProblemNotFound() {

        Assertions.assertThrows(AssertionError.class, () -> {
            mockMvc.perform(get("/problems/details/200"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("/error/object-not-found"));
        });
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = "ROLE_USER")
    void testViewJavaProblems() throws Exception {

        mockMvc.perform(get("/problems/java"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("javaProblems"))
                .andExpect(view().name("java-problems"));
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = "ROLE_USER")
    void testDeleteJavaProblem() throws Exception {

        mockMvc.perform(delete("/problems/java/delete-problem/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/problems/java"));

        Optional<ProblemEntity> problemOpt = problemRepository.findById(1L);

        Assertions.assertFalse(problemOpt.isPresent());
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = "ROLE_USER")
    void testViewCsharpProblems() throws Exception {

        mockMvc.perform(get("/problems/csharp"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("csharpProblems"))
                .andExpect(view().name("csharp-problems"));
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = "ROLE_USER")
    void testDeleteCsharpProblem() throws Exception {

        mockMvc.perform(delete("/problems/csharp/delete-problem/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/problems/csharp"));

        Optional<ProblemEntity> problemOpt = problemRepository.findById(1L);

        Assertions.assertFalse(problemOpt.isPresent());
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = "ROLE_USER")
    void testViewJavaScriptProblems() throws Exception {

        mockMvc.perform(get("/problems/javascript"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("javaScriptProblems"))
                .andExpect(view().name("javascript-problems"));
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = "ROLE_USER")
    void testDeleteJavaScriptProblem() throws Exception {

        mockMvc.perform(delete("/problems/javascript/delete-problem/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/problems/javascript"));

        Optional<ProblemEntity> problemOpt = problemRepository.findById(1L);

        Assertions.assertFalse(problemOpt.isPresent());
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = "ROLE_USER")
    void testViewPythonProblems() throws Exception {

        mockMvc.perform(get("/problems/python"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("pythonProblems"))
                .andExpect(view().name("python-problems"));
    }

    @Test
    @WithMockUser(username = "shristov16@e-dnrs.org", authorities = "ROLE_USER")
    void testDeletePythonScriptProblem() throws Exception {

        mockMvc.perform(delete("/problems/python/delete-problem/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/problems/python"));

        Optional<ProblemEntity> problemOpt = problemRepository.findById(1L);

        Assertions.assertFalse(problemOpt.isPresent());
    }


}