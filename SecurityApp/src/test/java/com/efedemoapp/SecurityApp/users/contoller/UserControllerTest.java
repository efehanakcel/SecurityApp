package com.efedemoapp.SecurityApp.users.contoller;


import com.efedemoapp.SecurityApp.users.controller.UserController;
import com.efedemoapp.SecurityApp.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testCreateUser_Success() throws Exception {
        when(userService.createUser("testuser", "test@example.com", "SecurePass123!")).thenReturn("User created");

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\": \"testuser\", \"email\": \"test@example.com\", \"password\": \"SecurePass123!\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateUser_Fail_UserExists() throws Exception {
        when(userService.createUser("testuser", "test@example.com", "SecurePass123!")).thenReturn("User already exists");

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\": \"testuser\", \"email\": \"test@example.com\", \"password\": \"SecurePass123!\"}"))
                .andExpect(status().isBadRequest());
    }
}