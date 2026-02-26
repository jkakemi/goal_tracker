package com.goaltracker.user.application.gateway;

import com.goaltracker.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User update(User user);
    User save(User user);

    Page<User> findAllActiveTrue(Pageable pageable);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
}
