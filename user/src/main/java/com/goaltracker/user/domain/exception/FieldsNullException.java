package com.goaltracker.user.domain.exception;

public class FieldsNullException extends RuntimeException {
    public FieldsNullException(){
        super("Required fields are null");
    }
}
