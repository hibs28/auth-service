package com.asianwear.auth_service.utils;

import com.asianwear.auth_service.dto.AddressRequest;
import com.asianwear.auth_service.entity.Address;
import com.asianwear.auth_service.entity.Role;
import com.asianwear.auth_service.entity.User;
import com.github.javafaker.Faker;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;

public abstract class Fixtures {
    public static UUID USER_ID = randomUUID();
    public static UUID ADMIN_ID = randomUUID();

    public static UUID ADDRESS_ID = randomUUID();

    static Faker faker = new Faker(new Locale("en-GB"));

    public static User createUser(UUID userId) {
       return User.builder()
               .userId(userId)
               .firstName(faker.name().firstName())
               .lastName(faker.name().lastName())
               .email(faker.internet().emailAddress())
               .password(faker.internet().password())
               .role(Role.USER)
               .build();
    }

    public static User FAKE_ADMIN(UUID adminId) {
        return User.builder()
                .userId(adminId)
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .role(Role.ADMIN)
                .build();
    }

    public static Address createFakeAddress(UUID userId) {
        return Address.builder()
                .addressId(ADDRESS_ID)
                .street_address(faker.address().streetAddress())
                .city(faker.address().city())
                .country(faker.address().country())
                .postal_code(faker.address().zipCode())
                .userId(userId)
                .build();
    }


    public static AddressRequest createAddressRequest() {
        return AddressRequest.builder()
                .street_address(faker.address().streetAddress())
                .city(faker.address().city())
                .country(faker.address().country())
                .postal_code(faker.address().zipCode())
                .build();
    }
}
