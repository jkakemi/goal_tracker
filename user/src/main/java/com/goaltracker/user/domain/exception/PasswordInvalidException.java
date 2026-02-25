package com.goaltracker.user.domain.exception;

public class PasswordInvalidException extends RuntimeException {
    public PasswordInvalidException() {
        super("\"Password must contain at least 8 characters,\\n\" +\n" +
                "                    \"including uppercase, lowercase,\\n\" +\n" +
                "                    \"number and special character. \"");
    }
}
