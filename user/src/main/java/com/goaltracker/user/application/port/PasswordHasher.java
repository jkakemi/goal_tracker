package com.goaltracker.user.application.port;

public interface PasswordHasher {
    String hash(String rawPassword);
}
