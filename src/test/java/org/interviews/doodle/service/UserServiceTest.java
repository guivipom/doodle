package org.interviews.doodle.service;

import org.interviews.doodle.dto.UserRequest;
import org.interviews.doodle.dto.UserResponse;
import org.interviews.doodle.entity.User;
import org.interviews.doodle.exception.UserNotFoundException;
import org.interviews.doodle.mapper.UserMapper;
import org.interviews.doodle.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserService service;

    @Test
    void createUser_savesAndReturnUserResponse() {
        UserRequest request = new UserRequest("Guillermo", "guillermo@example.com");
        User entity = new User();
        entity.setName("Guillermo");
        entity.setEmail("guillermo@example.com");

        User saved = new User();
        saved.setId(1L);
        saved.setName("Guillermo");
        saved.setEmail("guillermo@example.com");
        saved.setCreatedAt(LocalDateTime.now());

        UserResponse response = new UserResponse(1L, "Guillermo", "guillermo@example.com", saved.getCreatedAt());

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        UserResponse result = service.createUser(request);

        assertSame(response, result);
        verify(mapper).toEntity(request);
        verify(repository).save(entity);
        verify(mapper).toResponse(saved);
    }

    @Test
    void getUser_whenUserExists_returnsUserResponse() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        UserResponse response = new UserResponse(id, "Guillermo", "guillermo@example.com", LocalDateTime.now());

        when(repository.findById(id)).thenReturn(Optional.of(user));
        when(mapper.toResponse(user)).thenReturn(response);

        UserResponse result = service.getUser(id);

        assertSame(response, result);
        verify(repository).findById(id);
        verify(mapper).toResponse(user);
    }

    @Test
    void getUser_whenUserNotFound_throwsException() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.getUser(id));
        verify(repository).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    void findByEmail_whenUserExists_returnsResponse() {
        String email = "guillermo@example.com";
        User user = new User();
        UserResponse response = new UserResponse(1L, "Guillermo", email, LocalDateTime.now());

        when(repository.findByEmail(email)).thenReturn(Optional.of(user));
        when(mapper.toResponse(user)).thenReturn(response);

        UserResponse result = service.findByEmail(email);

        assertSame(response, result);
        verify(repository).findByEmail(email);
        verify(mapper).toResponse(user);
    }

    @Test
    void findByEmail_whenUserNotFound_throwsException() {
        String email = "guillermoNotFoundd@example.com";
        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.findByEmail(email));
        verify(repository).findByEmail(email);
        verifyNoInteractions(mapper);
    }

    @Test
    void getAllUsers_returnAllUsers() {
        User user1 = new User();
        User user2 = new User();
        user1.setId(1L);
        user2.setId(2L);

        UserResponse resp1 = new UserResponse(1L, "Guillermo Vidal", "guillermo@test.com", LocalDateTime.now());
        UserResponse resp2 = new UserResponse(2L, "Alejandro Vidal", "guillermo2@test.com", LocalDateTime.now());

        when(repository.findAll()).thenReturn(List.of(user1, user2));
        when(mapper.toResponse(user1)).thenReturn(resp1);
        when(mapper.toResponse(user2)).thenReturn(resp2);

        List<UserResponse> results = service.getAllUsers();

        assertEquals(2, results.size());
        assertSame(resp1, results.get(0));
        assertSame(resp2, results.get(1));
        verify(repository).findAll();
        verify(mapper).toResponse(user1);
        verify(mapper).toResponse(user2);
    }
}
