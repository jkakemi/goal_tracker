package com.goaltracker.user.application.command;

public record LoginCommand(
        String username,
        String password
) {
}
