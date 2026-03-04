package com.goaltracker.gamification.infrastructure.adapter.event;

import java.util.UUID;

public record MissionCompletedEvent(
        UUID userId,
        String category,
        Integer difficulty
) {
}
