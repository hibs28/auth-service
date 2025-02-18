package com.asianwear.auth_service.service;

import com.asianwear.auth_service.config.SecurityConfig;
import com.asianwear.auth_service.dto.AddressRequest;
import com.asianwear.auth_service.dto.RegisterRequest;
import com.asianwear.auth_service.entity.Address;
import com.asianwear.auth_service.entity.User;
import com.asianwear.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.asianwear.auth_service.entity.Role.USER;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressService addressService;
    public String register(RegisterRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already in use!");
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(USER);

        userRepository.save(user);
        addressService.saveAddress(user.getUserId(), request.getAddress());

        return "User registered successfully!";
    }


}
