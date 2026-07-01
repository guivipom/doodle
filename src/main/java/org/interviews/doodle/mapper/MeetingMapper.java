package org.interviews.doodle.mapper;

import org.interviews.doodle.dto.MeetingResponse;
import org.interviews.doodle.entity.Meeting;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, TimeSlotMapper.class})
public interface MeetingMapper {

    MeetingResponse toResponse(Meeting meeting);
}
