package org.interviews.doodle.service;

import lombok.AllArgsConstructor;
import org.interviews.doodle.dto.TimeSlotRequest;
import org.interviews.doodle.dto.TimeSlotResponse;
import org.interviews.doodle.entity.SlotStatus;
import org.interviews.doodle.entity.TimeSlot;
import org.interviews.doodle.entity.User;
import org.interviews.doodle.exception.SlotNotFoundException;
import org.interviews.doodle.exception.UserNotFoundException;
import org.interviews.doodle.mapper.TimeSlotMapper;
import org.interviews.doodle.repository.TimeSlotRepository;
import org.interviews.doodle.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository repository;
    private final UserRepository userRepository;
    private final TimeSlotMapper mapper;

    public TimeSlotResponse createSlot(Long userId, TimeSlotRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        TimeSlot slot = mapper.toEntity(request);
        slot.setUser(user);
        if (slot.getStatus() == null) {
            slot.setStatus(SlotStatus.FREE);}

            return mapper.toResponse(repository.save(slot));
    }

    public TimeSlotResponse getSlot(Long userId, Long slotId) {
        TimeSlot slot = repository.findById(slotId)
                .orElseThrow(() -> new SlotNotFoundException(slotId));
        if (!slot.getUser().getId().equals(userId)) {
            throw new SlotNotFoundException(slotId);
        }
        return mapper.toResponse(slot);
    }

    public List<TimeSlotResponse> getSlotsByUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        List<TimeSlot> slots = repository.findByUserId(userId);

        return slots.stream().map(mapper::toResponse).toList();
    }

    public TimeSlotResponse updateSlot(Long userId, Long slotId, TimeSlotRequest request) {
        TimeSlot slot = repository.findById(slotId)
                .orElseThrow(() -> new SlotNotFoundException(slotId));
        if (!slot.getUser().getId().equals(userId)) {
            throw new SlotNotFoundException(slotId);
        }
        slot.setStartTime(request.startTime());
        slot.setEndTime(request.endTime());
        slot.setStatus(request.status());

        return mapper.toResponse(repository.save(slot));
    }

    public void deleteSlot(Long userId, Long slotId) {
        TimeSlot slot = repository.findById(slotId)
                .orElseThrow(() -> new SlotNotFoundException(slotId));
        if (!slot.getUser().getId().equals(userId)) {
            throw new SlotNotFoundException(slotId);
        }
        repository.delete(slot);
    }

}
