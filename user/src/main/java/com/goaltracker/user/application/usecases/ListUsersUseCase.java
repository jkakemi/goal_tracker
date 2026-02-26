package com.goaltracker.user.application.usecases;

import com.goaltracker.user.application.gateway.UserRepository;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.domain.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public record ListUsersUseCase(UserRepository repository) {

    public Page<User> execute(Pageable pageable, UUID userId) {
        User user =  repository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return repository.findAllActiveTrue(pageable);
    }

}