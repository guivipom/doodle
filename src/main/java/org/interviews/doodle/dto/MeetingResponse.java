package org.interviews.doodle.dto;

import java.time.LocalDateTime;
import java.util.List;

public record MeetingResponse(Long id,
                              String title,
                              String description,
                              UserResponse organizer,
                              TimeSlotResponse slot,
                              List<UserResponse> participants,
                              LocalDateTime createdAt
) {
}
