package com.goaltracker.user.application.usecases;

import com.goaltracker.user.application.gateway.UserRepository;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.domain.exception.UserNotFoundException;

import java.util.UUID;

public record GetUserUseCase(UserRepository repository) {

    public User execute(UUID userId) {
        User user = repository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if(!user.isActive()){
            throw new UserNotFoundException();
        }

        return user;
    }

}