package com.goaltracker.mission.application.command;

import com.goaltracker.mission.domain.Category;

import java.time.LocalDateTime;

public record UpdateMissionCommand(
        String title,
        Category category,
        Integer difficulty,
        LocalDateTime deadline
        // mesma coisa mas caso precise de alteração, faz separado
) {

}
