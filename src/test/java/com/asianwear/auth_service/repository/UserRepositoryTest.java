package com.asianwear.auth_service.repository;

import com.asianwear.auth_service.entity.User;
import com.asianwear.auth_service.utils.Fixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Uses real DB, not H2
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void givenUser_whenSave_thenFindById() {
        User user = Fixtures.FAKE_USER();
        user.setEmail("test@example.com");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(user.getUserId());
        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

}
