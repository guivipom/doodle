package org.interviews.doodle.service;

import lombok.AllArgsConstructor;
import org.interviews.doodle.dto.UserRequest;
import org.interviews.doodle.dto.UserResponse;
import org.interviews.doodle.entity.User;
import org.interviews.doodle.exception.UserNotFoundException;
import org.interviews.doodle.mapper.UserMapper;
import org.interviews.doodle.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserResponse createUser(UserRequest request) {
        User user = mapper.toEntity(request);
        User saved = repository.save(user);
        return mapper.toResponse(saved);
    }

    public UserResponse getUser(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserResponse findByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toResponse)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public List<UserResponse> getAllUsers() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }
}
