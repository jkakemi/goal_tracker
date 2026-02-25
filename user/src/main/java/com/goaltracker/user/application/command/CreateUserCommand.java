package com.goaltracker.user.application.command;

import com.goaltracker.user.domain.SkillsEnum;

import java.util.List;

public record CreateUserCommand(
        String username,
        String email,
        String password,
        List<SkillsEnum> skills
) {
}
