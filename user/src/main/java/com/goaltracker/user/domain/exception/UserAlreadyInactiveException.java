package com.goaltracker.user.domain.exception;

public class UserAlreadyInactiveException extends RuntimeException {
    public UserAlreadyInactiveException() {
        super("The user is already inactive");
    }
}
