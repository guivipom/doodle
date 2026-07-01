package org.interviews.doodle.mapper;

import org.interviews.doodle.dto.UserRequest;
import org.interviews.doodle.dto.UserResponse;
import org.interviews.doodle.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User toEntity(UserRequest request);
}
