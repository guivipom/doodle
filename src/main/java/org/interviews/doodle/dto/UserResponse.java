package org.interviews.doodle.dto;

import java.time.LocalDateTime;

public record UserResponse(long id, String name, String email, LocalDateTime createdAt) {}
