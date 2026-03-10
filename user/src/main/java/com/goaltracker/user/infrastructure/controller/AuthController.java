package com.goaltracker.user.infrastructure.controller;

import com.goaltracker.user.application.command.LoginCommand;
import com.goaltracker.user.application.port.AuthResult;
import com.goaltracker.user.application.usecases.AuthenticateUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticateUser authenticateUser;

    public AuthController(AuthenticateUser authenticateUser) {
        this.authenticateUser = authenticateUser;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResult> login(@RequestBody LoginCommand command) {
        AuthResult result = authenticateUser.execute(command);
        return ResponseEntity.ok(result);
    }
}
