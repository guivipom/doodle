package org.interviews.doodle.dto;

import org.interviews.doodle.entity.SlotStatus;

import java.time.LocalDateTime;

public record TimeSlotRequest(LocalDateTime startTime, LocalDateTime endTime, SlotStatus status) {
}
