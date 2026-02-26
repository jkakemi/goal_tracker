package com.goaltracker.user.application.usecases;

import com.goaltracker.user.application.command.UpdateSkillsCommand;
import com.goaltracker.user.application.gateway.UserRepository;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.domain.exception.UserNotFoundException;

import java.util.UUID;

public record UpdateSkillsUseCase(UserRepository repository) {
    public User execute(UUID id, UpdateSkillsCommand command) {
        User user = repository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        if (command.add()) {
            user.addSkills(command.skills());
        } else {
            user.removeSkills(command.skills());
        }

        return user;
    }
}
