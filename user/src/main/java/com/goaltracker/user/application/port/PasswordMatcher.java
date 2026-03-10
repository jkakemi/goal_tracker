package com.goaltracker.user.application.port;

public interface PasswordMatcher {
    boolean matches(String raw, String hash);
}
