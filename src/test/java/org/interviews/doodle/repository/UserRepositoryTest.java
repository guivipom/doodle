package org.interviews.doodle.repository;

import org.interviews.doodle.TestcontainersConfiguration;
import org.interviews.doodle.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail_whenEmailExists_returnsUser() {
        User user = new User();
        user.setName("Guillermo Vidal");
        user.setEmail("guillermo@example.com");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("guillermo@example.com");

        assertTrue(found.isPresent());
        assertEquals("Guillermo Vidal", found.get().getName());
        assertEquals("guillermo@example.com", found.get().getEmail());
    }

    @Test
    void findByEmail_whenEmailDoesNotExist_returnsEmptyOptional() {

        assertTrue(userRepository.findByEmail("guillermoNotFound@example.com").isEmpty());
    }

    @Test
    void existsByEmail_whenEmailExists_returnsTrue() {
        User user = new User();
        user.setName("Guillermo Vidal");
        user.setEmail("guillermo@example.com");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);


        assertTrue(userRepository.existsByEmail("guillermo@example.com"));
    }

    @Test
    void existsByEmail_whenEmailDoesNotExist_returnsFalse() {

        assertFalse(userRepository.existsByEmail("guillermoNotFound@example.com"));
    }

}
