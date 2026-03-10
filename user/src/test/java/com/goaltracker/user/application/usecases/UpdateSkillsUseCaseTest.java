package com.goaltracker.user.application.usecases;

import com.goaltracker.user.application.command.UpdateSkillsCommand;
import com.goaltracker.user.application.gateway.UserRepository;
import com.goaltracker.user.domain.SkillsEnum;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.domain.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateSkillsUseCase - unit tests")
class UpdateSkillsUseCaseTest {

    @Mock
    UserRepository repository;

    private UpdateSkillsUseCase subject() {
        return new UpdateSkillsUseCase(repository);
    }

    @Test
    @DisplayName("Deve adicionar skills quando add=true")
    void shouldAddSkillsWhenAddTrue() {
        // Arrange
        User user = User.create("alice", "alice@example.com", "P@ssw0rd!", new HashSet<>());
        UUID id = user.getPublicId();

        UpdateSkillsCommand cmd = new UpdateSkillsCommand(true, Set.of(SkillsEnum.FINANCEIRO, SkillsEnum.SAUDE));

        when(repository.findById(id)).thenReturn(Optional.of(user));

        // Act
        User result = subject().execute(id, cmd);

        // Assert
        assertTrue(result.getSkills().contains(SkillsEnum.FINANCEIRO));
        assertTrue(result.getSkills().contains(SkillsEnum.SAUDE));

        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Deve remover skills quando add=false")
    void shouldRemoveSkillsWhenAddFalse() {
        // Arrange: skills MUTÁVEL (HashSet)
        Set<SkillsEnum> initialSkills = new HashSet<>(Set.of(SkillsEnum.FINANCEIRO, SkillsEnum.SAUDE));
        User user = User.create("bruno", "bruno@example.com", "P@ssw0rd!", initialSkills);
        UUID id = user.getPublicId();

        UpdateSkillsCommand cmd = new UpdateSkillsCommand(false, Set.of(SkillsEnum.FINANCEIRO));

        when(repository.findById(id)).thenReturn(Optional.of(user));

        // Act
        User result = subject().execute(id, cmd);

        // Assert
        assertFalse(result.getSkills().contains(SkillsEnum.FINANCEIRO));
        assertTrue(result.getSkills().contains(SkillsEnum.SAUDE));

        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Deve lançar UserNotFoundException quando usuário não existir")
    void shouldThrowWhenUserNotFound() {
        UUID id = UUID.randomUUID();
        UpdateSkillsCommand cmd = new UpdateSkillsCommand(true, Set.of(SkillsEnum.PRODUTIVIDADE));

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> subject().execute(id, cmd));

        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
    }
}