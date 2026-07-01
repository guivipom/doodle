package org.interviews.doodle.mapper;

import org.interviews.doodle.dto.TimeSlotRequest;
import org.interviews.doodle.dto.TimeSlotResponse;
import org.interviews.doodle.entity.TimeSlot;
import org.interviews.doodle.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TimeSlotMapper {

    @Mapping(target = "userId", source = "timeSlot.user.id")
    TimeSlotResponse toResponse(TimeSlot timeSlot);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    TimeSlot toEntity(TimeSlotRequest request);
}
