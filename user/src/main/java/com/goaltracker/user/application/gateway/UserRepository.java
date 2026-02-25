package com.goaltracker.user.application.gateway;

import com.goaltracker.user.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
    Optional<User> save(User user);
}
