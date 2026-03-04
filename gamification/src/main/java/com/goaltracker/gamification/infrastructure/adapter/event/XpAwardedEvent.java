package com.goaltracker.gamification.infrastructure.adapter.event;

import java.util.UUID;

public record XpAwardedEvent(
        UUID userId,
        Integer xpEarned
) {}