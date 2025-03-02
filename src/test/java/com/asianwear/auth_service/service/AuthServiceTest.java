package com.asianwear.auth_service.service;

import com.asianwear.auth_service.dto.RegisterRequest;
import com.asianwear.auth_service.dto.RegisterResponse;
import com.asianwear.auth_service.entity.User;
import com.asianwear.auth_service.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.asianwear.auth_service.utils.Fixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;


    @Test
    void givenValidRequest_whenRegister_thenUserIsCreatedSuccessfully() {
        RegisterRequest validRequest = VALID_REGISTER_REQUEST("hashedPassword");

        // Given
        when(userRepository.existsByEmail(validRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(validRequest.getPassword())).thenReturn("hashedPassword");

        User mockUser = createUserFromRequest(validRequest);

        User mockUserWithId = createUserWithIdFromRequest(validRequest);

        when(userRepository.save(mockUser)).thenReturn(mockUserWithId);

        // When
        RegisterResponse response = authService.register(validRequest);

        // Then
        assertNotNull(response);
        assertNotNull(response.getUserId());
        assertEquals("User registered successfully!", response.getMessage());

        verify(userRepository, times(1)).save(mockUser);
        verify(userRepository, times(1)).existsByEmail(validRequest.getEmail());
    }

    @Test
    void givenExistingEmail_whenRegister_thenThrowEntityExistsException() {
        RegisterRequest validRequest = VALID_REGISTER_REQUEST(null);

        // Given
        when(userRepository.existsByEmail(validRequest.getEmail())).thenReturn(true);

        // When & Then
        EntityExistsException thrown = assertThrows(EntityExistsException.class, () -> {
            authService.register(validRequest);
        });

        assertEquals("User with email " + validRequest.getEmail() + " already exists.", thrown.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void givenValidRequest_whenPasswordEncodingFails_thenThrowRuntimeException() {
        RegisterRequest validRequest = VALID_REGISTER_REQUEST(null);

        // Given
        when(userRepository.existsByEmail(validRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(validRequest.getPassword())).thenThrow(new RuntimeException("Hashing error"));

        // When & Then
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            authService.register(validRequest);
        });

        assertEquals("Hashing error", thrown.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

}