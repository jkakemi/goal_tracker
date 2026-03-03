package com.goaltracker.mission.application.event;

import com.goaltracker.mission.domain.Category;

import java.util.UUID;

public record MissionCompletedEvent(
        UUID userId,
        Category category,
        Integer difficulty
) {
}
