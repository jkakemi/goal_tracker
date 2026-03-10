package com.goaltracker.user.infrastructure.consumer;

import java.util.UUID;

public record XpMissionDto(
        UUID userId,
        double xpEarned
) {
}
