package com.goaltracker.user.infrastructure.controller.dto;

import com.goaltracker.user.domain.SkillsEnum;
import com.goaltracker.user.domain.User;

import java.util.Set;

public record UserDto(
        String username,
        String email,
        double xpTotal,
        int level,
        Set<SkillsEnum> skills
) {
    public UserDto(User user) {
        this(user.getUsername(), user.getEmail(), user.getXpTotal(), user.getLevel(), user.getSkills());
    }
}
