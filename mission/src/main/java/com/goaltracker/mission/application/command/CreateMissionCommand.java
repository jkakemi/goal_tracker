package com.goaltracker.mission.application.command;

import com.goaltracker.mission.domain.Category;

import java.time.LocalDateTime;

public record CreateMissionCommand(
        String title,
        Category category,
        Integer difficulty,
        LocalDateTime deadline
) {}
