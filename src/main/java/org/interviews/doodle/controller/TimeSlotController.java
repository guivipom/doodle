package org.interviews.doodle.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.interviews.doodle.dto.TimeSlotRequest;
import org.interviews.doodle.dto.TimeSlotResponse;
import org.interviews.doodle.service.TimeSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users/{userId}/slots")
@Tag(name = "Time Slots", description = "Time slot management operations")
public class TimeSlotController {

    private final TimeSlotService service;

    @Operation(summary = "Create an available time slot")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Time slot created successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping
    public ResponseEntity<TimeSlotResponse> createSlot(
            @Parameter(description = "ID of the user") @PathVariable Long userId,
            @RequestBody TimeSlotRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createSlot(userId, request));
    }

    @Operation(summary = "Get all time slots for a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of time slots retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping
    public ResponseEntity<List<TimeSlotResponse>> getSlots(
            @Parameter(description = "ID of the user") @PathVariable Long userId) {
        return ResponseEntity.ok(service.getSlotsByUser(userId));
    }

    @Operation(summary = "Get a specific time slot that belongs to the user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Time slot found"),
            @ApiResponse(responseCode = "404", description = "Time slot or user not found")
    })
    @GetMapping("/{slotId}")
    public ResponseEntity<TimeSlotResponse> getSlot(
            @Parameter(description = "ID of the user") @PathVariable Long userId,
            @Parameter(description = "ID of the time slot") @PathVariable Long slotId) {
        return ResponseEntity.ok(service.getSlot(userId, slotId));
    }

    @Operation(summary = "Update a time slot")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Time slot updated successfully"),
            @ApiResponse(responseCode = "404", description = "Time slot or user not found")
    })
    @PutMapping("/{slotId}")
    public ResponseEntity<TimeSlotResponse> updateSlot(
            @Parameter(description = "ID of the user") @PathVariable Long userId,
            @Parameter(description = "ID of the time slot") @PathVariable Long slotId,
            @RequestBody TimeSlotRequest request) {
        return ResponseEntity.ok(service.updateSlot(userId, slotId, request));
    }

    @Operation(summary = "Delete a time slot")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Time slot deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Time slot or user not found")
    })
    @DeleteMapping("/{slotId}")
    public ResponseEntity<Void> deleteSlot(
            @Parameter(description = "ID of the user") @PathVariable Long userId,
            @Parameter(description = "ID of the time slot") @PathVariable Long slotId) {
        service.deleteSlot(userId, slotId);
        return ResponseEntity.noContent().build();
    }

}
