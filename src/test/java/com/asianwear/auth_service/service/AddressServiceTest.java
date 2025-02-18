package com.asianwear.auth_service.service;

import com.asianwear.auth_service.dto.AddressRequest;
import com.asianwear.auth_service.entity.Address;
import com.asianwear.auth_service.entity.User;
import com.asianwear.auth_service.repository.AddressRepository;
import com.asianwear.auth_service.repository.UserRepository;
import com.asianwear.auth_service.utils.Fixtures;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.asianwear.auth_service.utils.Fixtures.USER_ID;
import static com.asianwear.auth_service.utils.Fixtures.createUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AddressService addressService;

    @Test
    public void givenUserExists_shouldSaveAddress() {
        //Given
        User mockUser = createUser(USER_ID);
        AddressRequest userAddress = Fixtures.createAddressRequest();
        //When
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
        String result = addressService.saveAddress(USER_ID, userAddress);
        //Then
        assertEquals("Address saved successfully!", result);
        verify(addressRepository, times(1)).save(any(Address.class));
    }

}