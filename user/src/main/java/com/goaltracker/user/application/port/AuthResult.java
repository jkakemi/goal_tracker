package com.goaltracker.user.application.port;

import java.time.Instant;

public record AuthResult(String token, Instant dataExpiracao) {
}
