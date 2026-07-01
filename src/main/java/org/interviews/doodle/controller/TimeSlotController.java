package org.interviews.doodle.controller;

import lombok.AllArgsConstructor;
import org.interviews.doodle.dto.TimeSlotRequest;
import org.interviews.doodle.dto.TimeSlotResponse;
import org.interviews.doodle.entity.SlotStatus;
import org.interviews.doodle.service.TimeSlotService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users/{userId}/slots")
public class TimeSlotController {

    private final TimeSlotService service;

    @PostMapping
    public ResponseEntity<TimeSlotResponse> createSlot(
            @PathVariable Long userId,
            @RequestBody TimeSlotRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createSlot(userId, request));
    }

    @GetMapping
    public ResponseEntity<List<TimeSlotResponse>> getSlots(
            @PathVariable Long userId) {
        return ResponseEntity.ok(service.getSlotsByUser(userId));
    }

    @GetMapping("/{slotId}")
    public ResponseEntity<TimeSlotResponse> getSlot(
            @PathVariable Long userId,
            @PathVariable Long slotId) {
        return ResponseEntity.ok(service.getSlot(userId, slotId));
    }

    @PutMapping("/{slotId}")
    public ResponseEntity<TimeSlotResponse> updateSlot(
            @PathVariable Long userId,
            @PathVariable Long slotId,
            @RequestBody TimeSlotRequest request) {
        return ResponseEntity.ok(service.updateSlot(userId, slotId, request));
    }

    @DeleteMapping("/{slotId}")
    public ResponseEntity<Void> deleteSlot(
            @PathVariable Long userId,
            @PathVariable Long slotId) {
        service.deleteSlot(userId, slotId);
        return ResponseEntity.noContent().build();
    }


}
