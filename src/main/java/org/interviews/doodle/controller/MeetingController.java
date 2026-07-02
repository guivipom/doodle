package org.interviews.doodle.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Meetings", description = "Meeting scheduling operations")
public class MeetingController {

    private final MeetingService service;

    @Operation(summary = "Create a meeting", description = "Converts a free slot into a meeting and marks it as busy.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Meeting created successfully"),
            @ApiResponse(responseCode = "404", description = "Organizer, slot, or participant not found"),
            @ApiResponse(responseCode = "409", description = "Slot is Busy")
    })
    @PostMapping
    public ResponseEntity<MeetingResponse> createMeeting(
            @Parameter(description = "Id of the user organizer") @PathVariable Long organizerId,
            @RequestBody MeetingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createMeeting(organizerId, request));
    }

    @Operation(summary = "Get all meetings for an organizer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of meetings retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping
    public ResponseEntity<List<MeetingResponse>> getMeetingsByOrganizer(
            @Parameter(description = "Id of the meeting organizer") @PathVariable Long organizerId) {
        return ResponseEntity.ok(service.getMeetingsByOrganizer(organizerId));
    }

    @Operation(summary = "Get a meeting by Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meeting found"),
            @ApiResponse(responseCode = "404", description = "Meeting not found")
    })
    @GetMapping("/{meetingId}")
    public ResponseEntity<MeetingResponse> getMeeting(
            @Parameter(description = "ID of the meeting organizer") @PathVariable Long organizerId,
            @Parameter(description = "ID of the meeting") @PathVariable Long meetingId) {
        return ResponseEntity.ok(service.getMeeting(meetingId));
    }

}
