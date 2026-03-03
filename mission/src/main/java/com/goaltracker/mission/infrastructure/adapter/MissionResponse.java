package com.goaltracker.mission.infrastructure.adapter;

import com.goaltracker.mission.domain.Category;
import com.goaltracker.mission.domain.StatusMission;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record MissionResponse (
        Long id,
        UUID userId,
        String title,
        Category category,
        Integer difficulty,
        LocalDateTime deadline,
        StatusMission statusMission,
        Instant createdAt
){
}
