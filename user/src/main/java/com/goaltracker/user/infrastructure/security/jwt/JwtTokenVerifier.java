package com.goaltracker.user.infrastructure.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.goaltracker.user.application.port.TokenVerifier;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record JwtTokenVerifier(String secret, String issuer) implements TokenVerifier {

    public JwtTokenVerifier(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.issuer}") String issuer
    ) {
        this.secret = secret;
        this.issuer = issuer;
    }

    @Override
    public Optional<UUID> extractUserId(String token) {
        try {
            DecodedJWT jwt = verify(token);
            return Optional.of(UUID.fromString(jwt.getSubject()));
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }


    @Override
    public boolean isValid(String token) {
        try {
            verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    private DecodedJWT verify(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();

        return verifier.verify(token);
    }

    public List<String> extractRoles(String token) {
        DecodedJWT jwt = JWT.decode(token);

        List<String> roles = jwt.getClaim("roles").asList(String.class);

        if (roles == null || roles.isEmpty()) {
            String scope = jwt.getClaim("scope").asString();
            if (scope != null) {
                roles = List.of(scope);
            }
        }

        return roles == null ? List.of() : roles;
    }

}
