package org.interviews.doodle.mapper;

import org.interviews.doodle.dto.TimeSlotRequest;
import org.interviews.doodle.dto.TimeSlotResponse;
import org.interviews.doodle.entity.SlotStatus;
import org.interviews.doodle.entity.TimeSlot;
import org.interviews.doodle.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TimeSlotMapperTest {

    private final TimeSlotMapper mapper = new TimeSlotMapperImpl();

    @Test
    void toResponse_shouldMapAllFields() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User();
        user.setId(1L);

        TimeSlot slot = new TimeSlot();
        slot.setId(1L);
        slot.setUser(user);
        slot.setStartTime(now);
        slot.setEndTime(now.plusHours(1));
        slot.setStatus(SlotStatus.FREE);
        slot.setCreatedAt(now);

        TimeSlotResponse response = mapper.toResponse(slot);

        assertEquals(1L, response.id());
        assertEquals(1L, response.userId());
        assertEquals(now, response.startTime());
        assertEquals(now.plusHours(1), response.endTime());
        assertEquals(SlotStatus.FREE, response.status());
        assertEquals(now, response.createdAt());
    }

    @Test
    void toEntity_shouldMapStartEndAndStatus() {
        LocalDateTime now = LocalDateTime.now();
        TimeSlotRequest request = new TimeSlotRequest(now, now.plusHours(1), SlotStatus.BUSY);

        TimeSlot slot = mapper.toEntity(request);

        assertEquals(now, slot.getStartTime());
        assertEquals(now.plusHours(1), slot.getEndTime());
        assertEquals(SlotStatus.BUSY, slot.getStatus());
        assertNull(slot.getId());
        assertNull(slot.getUser());
        assertNull(slot.getCreatedAt());
    }

}
