package org.interviews.doodle.dto;

import org.interviews.doodle.entity.SlotStatus;

import java.time.LocalDateTime;

public record TimeSlotResponse(long id,
                               long userId,
                               LocalDateTime startTime,
                               LocalDateTime endTime,
                               SlotStatus status ,
                               LocalDateTime createdAt) {
}
