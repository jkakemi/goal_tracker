package com.goaltracker.user.domain.exception;

public class PasswordInvalidException extends RuntimeException {
    public PasswordInvalidException() {
        super(" 'Password must contain at least 8 characters,"  +
                " including uppercase, lowercase," +
                " number and special character. ");
    }
}
