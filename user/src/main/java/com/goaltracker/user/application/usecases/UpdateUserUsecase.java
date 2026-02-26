package com.goaltracker.user.application.usecases;

import com.goaltracker.user.application.command.UpdateUserCommand;
import com.goaltracker.user.application.gateway.UserRepository;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.domain.exception.EmailAlreadyExistsException;
import com.goaltracker.user.domain.exception.UserNotFoundException;
import com.goaltracker.user.domain.exception.UsernameAlreadyExistsException;

import java.util.UUID;

public record UpdateUserUsecase(UserRepository repository) {
    public User execute(UpdateUserCommand command, UUID publicId) {

        User user = repository.findById(publicId)
                .orElseThrow(UserNotFoundException::new);


        if (command.username() != null) {
            repository.findByUsername(command.username()).ifPresent(u -> {
                throw new UsernameAlreadyExistsException();
            });
            user.updateUsername(command.username());
        }

        if (command.email() != null) {
            repository.findByEmail(command.email())
                    .ifPresent(u -> {
                        throw new EmailAlreadyExistsException();
                    });

            user.updateEmail(command.email());
        }

        user.updatedAtNew();

        return repository.update(user);

    }

}
