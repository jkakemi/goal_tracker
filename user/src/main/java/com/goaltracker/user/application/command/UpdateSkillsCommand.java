package com.goaltracker.user.application.command;

import com.goaltracker.user.domain.SkillsEnum;

import java.util.Set;

public record UpdateSkillsCommand(
        boolean add,
        Set<SkillsEnum> skills
) {
}
