package com.efedemoapp.SecurityApp.users.contoller;

import com.efedemoapp.SecurityApp.users.controller.BookController;
import com.efedemoapp.SecurityApp.users.model.Books;
import com.efedemoapp.SecurityApp.users.service.BookService;
import com.efedemoapp.SecurityApp.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void testGetBooks_Success() throws Exception {
        when(bookService.getBooksByUserId(anyLong())).thenReturn(List.of(new Books()));
        mockMvc.perform(get("/api/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBooks_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
