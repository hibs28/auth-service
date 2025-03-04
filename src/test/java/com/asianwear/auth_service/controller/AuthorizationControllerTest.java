package com.asianwear.auth_service.controller;

import com.asianwear.auth_service.dto.RegisterRequest;
import com.asianwear.auth_service.dto.RegisterResponse;
import com.asianwear.auth_service.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.asianwear.auth_service.utils.Fixtures.VALID_REGISTER_REQUEST;
import static com.asianwear.auth_service.utils.Fixtures.VALID_REGISTER_RESPONSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuthorizationController.class)
class AuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenValidRequest_whenRegister_thenReturnCreatedStatus() throws Exception {
        // Given
        RegisterRequest validRequest = VALID_REGISTER_REQUEST("hashedPassword");

        RegisterResponse mockResponse = VALID_REGISTER_RESPONSE();
        when(authService.register(any(RegisterRequest.class))).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").isNotEmpty())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }

    @Test
    void givenExistingEmail_whenRegister_thenReturnConflictStatus() throws Exception {
        RegisterRequest validRequest = VALID_REGISTER_REQUEST(null);

        when(authService.register(any(RegisterRequest.class))).thenThrow(new EntityExistsException("User with this email " + validRequest.getEmail() + " already exists."));

        // When & Then
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isConflict())
                .andExpect(content().string("User with this email " + validRequest.getEmail() + " already exists."));
    }


    @Test
    void givenInvalidEmail_whenRegister_thenReturnBadRequest() throws Exception {
        // Given
        RegisterRequest invalidEmail = RegisterRequest.builder()
                .firstName("GNMTKTMGT").lastName("bfhfbdhfbh").email("email").password("Passsword2345!").build();

//        validRequest.setEmail("invalid-email");

        // When & Then
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmail)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Invalid email format"));
    }

    @Test
    void givenShortPassword_whenRegister_thenReturnBadRequest() throws Exception {
        // Given
        RegisterRequest validRequest = VALID_REGISTER_REQUEST("123");

        // When & Then
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }
}