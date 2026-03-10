package com.goaltracker.user.application.port;

import com.goaltracker.user.domain.Role;

import java.util.UUID;

public interface TokenGenerator {
    AuthResult generate(UUID userId, String email, Role role);
}
