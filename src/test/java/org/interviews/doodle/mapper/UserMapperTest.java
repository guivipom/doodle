package org.interviews.doodle.mapper;

import org.interviews.doodle.dto.UserRequest;
import org.interviews.doodle.dto.UserResponse;
import org.interviews.doodle.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = new UserMapperImpl();

    @Test
    void toResponse_shouldMapAllFields() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User();
        user.setId(42L);
        user.setName("Guillermo Vidal");
        user.setEmail("guillermo@example.com");
        user.setCreatedAt(now);

        UserResponse response = mapper.toResponse(user);

        assertEquals(42L, response.id());
        assertEquals("Guillermo Vidal", response.name());
        assertEquals("guillermo@example.com", response.email());
        assertEquals(now, response.createdAt());
    }

    @Test
    void toEntity_shouldMapNameAndEmail() {
        UserRequest request = new UserRequest("Guillermo Vidal", "guillermo@example.com");

        User user = mapper.toEntity(request);

        assertEquals("Guillermo Vidal", user.getName());
        assertEquals("guillermo@example.com", user.getEmail());
        assertNull(user.getId());
        assertNull(user.getCreatedAt());
    }

}
