package com.asianwear.auth_service.utils;

import com.asianwear.auth_service.dto.RegisterRequest;
import com.asianwear.auth_service.dto.RegisterResponse;
import com.asianwear.auth_service.entity.Role;
import com.asianwear.auth_service.entity.User;
import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.UUID;

import static java.util.UUID.randomUUID;

public abstract class Fixtures {
    public static UUID USER_ID = randomUUID();
    public static UUID ADMIN_ID = randomUUID();

    static Faker faker = new Faker(new Locale("en-GB"));

    public static User createUserFromRequest(RegisterRequest request) {
       return User.builder()
               .firstName(request.getFirstName())
               .lastName(request.getLastName())
               .email(request.getEmail())
               .password(request.getPassword())
               .role(Role.USER)
               .build();
    }

    public static User createUserWithIdFromRequest(RegisterRequest request) {
        return User.builder()
                .userId(USER_ID)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.USER)
                .build();
    }

    public static RegisterRequest VALID_REGISTER_REQUEST(String password) {
        return RegisterRequest.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(password != null && !password.isEmpty() ? password : faker.internet().password(8,15))
                .build();
    }

    public static RegisterResponse VALID_REGISTER_RESPONSE() {
        return RegisterResponse.builder()
                .userId(USER_ID)
                .message("User registered successfully!")
                .build();
    }

        public static User FAKE_USER() {
        return User.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .role(Role.USER)
                .build();
    }

//    public static User FAKE_ADMIN(UUID adminId) {
//        return User.builder()
//                .userId(adminId)
//                .firstName(faker.name().firstName())
//                .lastName(faker.name().lastName())
//                .email(faker.internet().emailAddress())
//                .password(faker.internet().password())
//                .role(Role.ADMIN)
//                .build();
//    }

}
