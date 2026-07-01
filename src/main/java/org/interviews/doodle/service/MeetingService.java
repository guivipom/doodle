package org.interviews.doodle.service;

import lombok.AllArgsConstructor;
import org.interviews.doodle.dto.MeetingRequest;
import org.interviews.doodle.dto.MeetingResponse;
import org.interviews.doodle.entity.Meeting;
import org.interviews.doodle.entity.SlotStatus;
import org.interviews.doodle.entity.TimeSlot;
import org.interviews.doodle.entity.User;
import org.interviews.doodle.exception.MeetingNotFoundException;
import org.interviews.doodle.exception.SlotNotAvailableException;
import org.interviews.doodle.exception.SlotNotFoundException;
import org.interviews.doodle.exception.UserNotFoundException;
import org.interviews.doodle.mapper.MeetingMapper;
import org.interviews.doodle.repository.MeetingRepository;
import org.interviews.doodle.repository.TimeSlotRepository;
import org.interviews.doodle.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final UserRepository userRepository;
    private final MeetingMapper mapper;

    @Transactional
    public MeetingResponse createMeeting(Long organizerId, MeetingRequest request) {
        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(organizerId));

        TimeSlot slot = timeSlotRepository.findById(request.slotId())
                .orElseThrow(() -> new SlotNotFoundException(request.slotId()));

        if (slot.getStatus() != SlotStatus.FREE) {
            throw new SlotNotAvailableException(request.slotId());
        }

        List<User> participants = resolveParticipants(request.participantIds());

        slot.setStatus(SlotStatus.BUSY);
        timeSlotRepository.save(slot);

        Meeting meeting = new Meeting();
        meeting.setTitle(request.title());
        meeting.setDescription(request.description());
        meeting.setOrganizer(organizer);
        meeting.setSlot(slot);
        meeting.setParticipants(participants);

        return mapper.toResponse(meetingRepository.save(meeting));
    }

    @Transactional(readOnly = true)
    public MeetingResponse getMeeting(Long id) {
        return meetingRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new MeetingNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<MeetingResponse> getMeetingsByOrganizer(Long organizerId) {
        userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(organizerId));

        return meetingRepository.findByOrganizerId(organizerId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    private List<User> resolveParticipants(List<Long> participantIds) {
        if (participantIds == null || participantIds.isEmpty()) {
            return List.of();
        }
        return participantIds.stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException(id)))
                .toList();
    }

}
