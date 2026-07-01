package org.interviews.doodle.service;

import org.interviews.doodle.dto.TimeSlotRequest;
import org.interviews.doodle.dto.TimeSlotResponse;
import org.interviews.doodle.entity.SlotStatus;
import org.interviews.doodle.entity.TimeSlot;
import org.interviews.doodle.entity.User;
import org.interviews.doodle.mapper.TimeSlotMapper;
import org.interviews.doodle.repository.TimeSlotRepository;
import org.interviews.doodle.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TimeSlotServiceTest {

    @Mock
    private TimeSlotRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TimeSlotMapper mapper;

    @InjectMocks
    private TimeSlotService service;

    private final Long userId = 1L;
    private final Long slotId = 2L;
    private final LocalDateTime now = LocalDateTime.now();


    @Test
    void createSlot_shouldCreateAndReturnResponse() {
        TimeSlotRequest request = new TimeSlotRequest(now, now.plusHours(1), SlotStatus.FREE);
        User user = new User();
        user.setId(userId);

        TimeSlot entity = new TimeSlot();
        TimeSlot saved = new TimeSlot();
        saved.setId(slotId);
        saved.setUser(user);

        TimeSlotResponse response = new TimeSlotResponse(slotId, userId, now, now.plusHours(1), SlotStatus.FREE, now);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        TimeSlotResponse result = service.createSlot(userId, request);

        assertSame(response, result);
        assertSame(user, entity.getUser());
        verify(userRepository).findById(userId);
        verify(mapper).toEntity(request);
        verify(repository).save(entity);
        verify(mapper).toResponse(saved);
    }

    @Test
    void getSlotsByUser_withoutStatus_returnsAllSlots() {
        User user = new User();
        user.setId(userId);

        TimeSlot slot1 = new TimeSlot();
        TimeSlot slot2 = new TimeSlot();
        TimeSlotResponse resp1 = new TimeSlotResponse(1L, userId, now, now.plusHours(1), SlotStatus.FREE, now);
        TimeSlotResponse resp2 = new TimeSlotResponse(2L, userId, now, now.plusHours(2), SlotStatus.BUSY, now);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(repository.findByUserId(userId)).thenReturn(List.of(slot1, slot2));
        when(mapper.toResponse(slot1)).thenReturn(resp1);
        when(mapper.toResponse(slot2)).thenReturn(resp2);

        List<TimeSlotResponse> results = service.getSlotsByUser(userId);

        assertEquals(2, results.size());

    }


}
