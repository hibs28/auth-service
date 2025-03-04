package com.asianwear.auth_service.service;

import com.asianwear.auth_service.dto.RegisterRequest;
import com.asianwear.auth_service.dto.RegisterResponse;
import com.asianwear.auth_service.entity.Role;
import com.asianwear.auth_service.entity.User;
import com.asianwear.auth_service.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public RegisterResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new EntityExistsException("User with email " + request.getEmail() + " already exists.");
        }

        User newUser = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(newUser);

        return new RegisterResponse(savedUser.getUserId(), "User registered successfully!") ;
    }


}
