package com.asianwear.auth_service.service;

import com.asianwear.auth_service.dto.AddressRequest;
import com.asianwear.auth_service.entity.Address;
import com.asianwear.auth_service.entity.User;
import com.asianwear.auth_service.repository.AddressRepository;
import com.asianwear.auth_service.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    public String saveAddress(UUID userId, AddressRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with this ID: " + userId)); // Get the User
        Address address = new Address();
        address.setStreet_address(request.getStreet_address());
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());
        address.setPostal_code(request.getPostal_code());
        address.setUserId(userId);

        addressRepository.save(address);
        return "Address saved successfully!";

    }

}
