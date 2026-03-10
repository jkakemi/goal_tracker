package com.goaltracker.user.application.usecases;

import com.goaltracker.user.application.gateway.UserRepository;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.domain.exception.UserNotFoundException;

import java.util.UUID;

public record CalculatorXpUseCase(UserRepository repository) {

    public User execute(UUID userId, double xp) {
        User user = repository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if(!user.userIsActive()){
            throw new UserNotFoundException();
        }

        user.addXP(xp);
        repository.update(user);

        return user;
    }

}