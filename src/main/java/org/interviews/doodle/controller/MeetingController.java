package org.interviews.doodle.controller;

import lombok.AllArgsConstructor;
import org.interviews.doodle.dto.MeetingRequest;
import org.interviews.doodle.dto.MeetingResponse;
import org.interviews.doodle.service.MeetingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users/{organizerId}/meetings")
public class MeetingController {

    private final MeetingService service;

    @PostMapping
    public ResponseEntity<MeetingResponse> createMeeting(
            @PathVariable Long organizerId,
            @RequestBody MeetingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createMeeting(organizerId, request));
    }

    @GetMapping
    public ResponseEntity<List<MeetingResponse>> getMeetingsByOrganizer(
            @PathVariable Long organizerId) {
        return ResponseEntity.ok(service.getMeetingsByOrganizer(organizerId));
    }

    @GetMapping("/{meetingId}")
    public ResponseEntity<MeetingResponse> getMeeting(
            @PathVariable Long organizerId,
            @PathVariable Long meetingId) {
        return ResponseEntity.ok(service.getMeeting(meetingId));
    }


}
