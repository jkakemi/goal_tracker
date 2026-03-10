package com.goaltracker.user.application.usecases;

import com.goaltracker.user.application.gateway.UserRepository;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.domain.SkillsEnum;
import com.goaltracker.user.domain.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculatorXpUseCaseTest {

    @Mock
    UserRepository repository;

    private CalculatorXpUseCase useCase() {
        return new CalculatorXpUseCase(repository);
    }

    @Test
    @DisplayName("Deve adicionar XP quando usuário existe e está ativo")
    void shouldAddXpWhenUserIsActive() {
        // Arrange
        UUID userId = UUID.randomUUID();
        double initialXp = 100.0;
        double xpToAdd = 50.0;

        User user = new User(
                userId,
                "joao",
                "joao@example.com",
                Set.<SkillsEnum>of(),
                "password",
                Instant.now()
        );

        // simula XP inicial
        user.addXP(initialXp);

        when(repository.findById(userId)).thenReturn(Optional.of(user));

        CalculatorXpUseCase useCase = useCase();

        // Act
        User result = useCase.execute(userId, xpToAdd);

        // Assert
        assertNotNull(result);
        assertEquals(initialXp + xpToAdd, result.getXpTotal(), "XP deve ser incrementado corretamente");

        verify(repository, times(1)).update(user);
    }

    @Test
    @DisplayName("Deve lançar UserNotFoundException quando usuário não existir")
    void shouldThrowWhenUserNotFound() {
        // Arrange
        UUID userId = UUID.randomUUID();

        when(repository.findById(userId)).thenReturn(Optional.empty());

        CalculatorXpUseCase useCase = useCase();

        // Act & Assert
        assertThrows(UserNotFoundException.class,
                () -> useCase.execute(userId, 50.0));

        verify(repository, never()).update(any());
    }

    @Test
    @DisplayName("Deve lançar UserNotFoundException quando usuário estiver inativo")
    void shouldThrowWhenUserIsInactive() {
        // Arrange
        UUID userId = UUID.randomUUID();

        User user = new User(
                userId,
                "maria",
                "maria@example.com",
                Set.<SkillsEnum>of(),
                "password",
                Instant.now()
        );

        // deixa o usuário inativo
        user.deactivate();

        when(repository.findById(userId)).thenReturn(Optional.of(user));

        CalculatorXpUseCase useCase = useCase();

        // Act & Assert
        assertThrows(UserNotFoundException.class,
                () -> useCase.execute(userId, 20.0));

        verify(repository, never()).update(any());
    }

}