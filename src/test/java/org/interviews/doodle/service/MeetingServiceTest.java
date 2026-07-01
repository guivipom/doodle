package org.interviews.doodle.service;

import org.interviews.doodle.dto.MeetingRequest;
import org.interviews.doodle.dto.MeetingResponse;
import org.interviews.doodle.entity.Meeting;
import org.interviews.doodle.entity.SlotStatus;
import org.interviews.doodle.entity.TimeSlot;
import org.interviews.doodle.entity.User;
import org.interviews.doodle.exception.SlotNotAvailableException;
import org.interviews.doodle.exception.SlotNotFoundException;
import org.interviews.doodle.exception.UserNotFoundException;
import org.interviews.doodle.mapper.MeetingMapper;
import org.interviews.doodle.repository.MeetingRepository;
import org.interviews.doodle.repository.TimeSlotRepository;
import org.interviews.doodle.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeetingServiceTest {
    @Mock
    MeetingRepository meetingRepository;
    @Mock
    TimeSlotRepository timeSlotRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    MeetingMapper mapper;

    @InjectMocks
    MeetingService service;

    private User organizer;
    private TimeSlot freeSlot;

    @BeforeEach
    void setUp() {
        organizer = new User();
        organizer.setId(1L);
        organizer.setName("Guillermo");
        organizer.setEmail("guillermo@example.com");

        freeSlot = new TimeSlot();
        freeSlot.setId(1L);
        freeSlot.setStatus(SlotStatus.FREE);
        freeSlot.setUser(organizer);
    }

    @Test
    void createMeeting_whenSlotIsFree_meetingIsCreated() {
        MeetingRequest request = new MeetingRequest(1L, "Title", "description", List.of());
        Meeting savedMeeting = new Meeting();
        MeetingResponse expectedResponse = new MeetingResponse(1L, "Title", "description",
                null,null, List.of(), LocalDateTime.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(organizer));
        when(timeSlotRepository.findById(1L)).thenReturn(Optional.of(freeSlot));
        when(meetingRepository.save(any())).thenReturn(savedMeeting);
        when(mapper.toResponse(savedMeeting)).thenReturn(expectedResponse);

        MeetingResponse result = service.createMeeting(1L, request);

        assertEquals(expectedResponse, result);
        assertEquals(SlotStatus.BUSY, freeSlot.getStatus()); // slot was marked BUSY
    }

    @Test
    void createMeeting_whenSlotIsBusy_throwsSlotNotAvailableException() {
        freeSlot.setStatus(SlotStatus.BUSY);
        MeetingRequest request = new MeetingRequest(1L, "Title", null, List.of());

        when(userRepository.findById(1L)).thenReturn(Optional.of(organizer));
        when(timeSlotRepository.findById(1L)).thenReturn(Optional.of(freeSlot));

        assertThrows(SlotNotAvailableException.class,
                () -> service.createMeeting(1L, request));
    }

    @Test
    void createMeeting_whenOrganizerNotFound_throwsUserNotFoundException() {
        MeetingRequest request = new MeetingRequest(1L, "Title", null, List.of());

        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> service.createMeeting(2L, request));
    }

    @Test
    void createMeeting_whenParticipantNotFound_throwsUserNotFoundException() {
        MeetingRequest request = new MeetingRequest(1L, "Title", null, List.of(99L));

        when(userRepository.findById(1L)).thenReturn(Optional.of(organizer));
        when(timeSlotRepository.findById(1L)).thenReturn(Optional.of(freeSlot));
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.createMeeting(1L, request));
    }


}
