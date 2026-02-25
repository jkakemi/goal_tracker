package com.goaltracker.user.domain.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(){
        super("Invalid email");
    }
}
