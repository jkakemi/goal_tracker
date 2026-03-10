package com.goaltracker.user.application.usecases;

import com.goaltracker.user.application.gateway.UserRepository;
import com.goaltracker.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public record ListUsersUseCase(UserRepository repository) {

    public Page<User> execute(Pageable pageable) {
        return repository.findAllActiveTrue(pageable);
    }

}