package com.goaltracker.user.infrastructure.controller.dto;

import com.goaltracker.user.domain.SkillsEnum;

import java.util.Set;

public record UpdateSkillsDto(
        boolean add,
        Set<SkillsEnum> skills
) {
}

