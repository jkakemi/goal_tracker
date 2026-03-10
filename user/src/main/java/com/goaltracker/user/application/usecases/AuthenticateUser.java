package com.goaltracker.user.application.usecases;

import com.goaltracker.user.application.command.LoginCommand;
import com.goaltracker.user.application.gateway.UserRepository;
import com.goaltracker.user.application.port.AuthResult;
import com.goaltracker.user.application.port.PasswordMatcher;
import com.goaltracker.user.application.port.TokenGenerator;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.domain.exception.InvalidCredentialsException;
import com.goaltracker.user.domain.exception.UserNotFoundException;

public record AuthenticateUser(UserRepository userRepository, PasswordMatcher passwordMatcher,
                               TokenGenerator tokenGenerator) {

    public AuthResult execute(LoginCommand command) {
        User user = userRepository.findByUsername(command.username())
                .orElseThrow(UserNotFoundException::new);

        user.ensureCanAuthenticate();

        if (!passwordMatcher.matches(command.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        return tokenGenerator.generate(user.getPublicId(), user.getUsername(), user.getRole());

    }
}
