package org.interviews.doodle.controller;

import org.interviews.doodle.dto.UserRequest;
import org.interviews.doodle.dto.UserResponse;
import org.interviews.doodle.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService service;

    @Test
    void createUser_shouldReturn201() throws Exception {
        UserRequest request = new UserRequest("Guillermo", "guillermo@example.com");
        UserResponse response = new UserResponse(1L, "Guillermo", "guillermo@example.com", LocalDateTime.now());

        when(service.createUser(request)).thenReturn(response);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Guillermo"))
                .andExpect(jsonPath("$.email").value("guillermo@example.com"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());


    }

    @Test
    void getUser_whenUserExists_shouldReturn200() throws Exception {
        UserResponse response = new UserResponse(1L, "Guillermo", "guillermo@example.com", LocalDateTime.now());

        when(service.getUser(1L)).thenReturn(response);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Guillermo"))
                .andExpect(jsonPath("$.email").value("guillermo@example.com"));
    }

}
