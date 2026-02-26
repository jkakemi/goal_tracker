package com.goaltracker.user.application.command;

import com.goaltracker.user.domain.SkillsEnum;

import java.util.Set;

public record UpdateUserCommand(
        String username,
        String email,
        String password,
        Set<SkillsEnum> skills
) {
}
