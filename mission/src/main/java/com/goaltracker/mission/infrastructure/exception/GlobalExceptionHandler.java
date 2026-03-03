package com.goaltracker.mission.infrastructure.exception;

import com.goaltracker.mission.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> handleDomainException(DomainException ex){
        Map<String, Object> body = Map.of(
                "timestamp",
                Instant.now(),
                "status",
                HttpStatus.BAD_REQUEST.value(),
                "error",
                "Regra de Negócio Violada",
                "message",
                ex.getMessage()
        );
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
