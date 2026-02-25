package com.goaltracker.user.application.usecases;

import com.goaltracker.user.application.command.CreateUserCommand;
import com.goaltracker.user.application.gateway.UserRepository;
import com.goaltracker.user.application.port.PasswordHasher;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.domain.exception.EmailAlreadyExistsException;
import com.goaltracker.user.domain.exception.UsernameAlreadyExistsException;

import java.util.Optional;

public record CreateUserUseCase(UserRepository repository, PasswordHasher passwordHasher) {
    public Optional<User> execute(CreateUserCommand command){

        repository.findByUsername(command.username()).ifPresent(u -> {
            throw new UsernameAlreadyExistsException();
        });

        repository.findByEmail(command.email()).ifPresent(u -> {
            throw new EmailAlreadyExistsException();
        });

        String passwordHash = passwordHasher.hash(command.password());

        User user = User.create(
                command.username(),
                command.email(),
                passwordHash,
                command.skills()
        );
        return repository.save(user);
    }
}
