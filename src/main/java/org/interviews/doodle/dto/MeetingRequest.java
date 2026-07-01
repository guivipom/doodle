package org.interviews.doodle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MeetingRequest(@NotNull Long slotId,
                             @NotBlank String title,
                             String description,
                             List<Long> participantIds
) {}
