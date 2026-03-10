package com.goaltracker.user.infrastructure.controller.dto;

import com.goaltracker.user.domain.SkillsEnum;

import java.util.Set;

public record UserCreateDto(
        String username,
        String email,
        String password,
        Set<SkillsEnum> skills
) {
}
