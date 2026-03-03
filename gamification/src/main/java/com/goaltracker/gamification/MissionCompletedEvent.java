package com.goaltracker.gamification;

import java.util.UUID;

public record MissionCompletedEvent(
        UUID userId,
        String category,
        Integer difficulty
) {
}
