package com.goaltracker.user.application.usecases;

import com.goaltracker.user.application.command.LoginCommand;
import com.goaltracker.user.application.gateway.UserRepository;
import com.goaltracker.user.application.port.AuthResult;
import com.goaltracker.user.application.port.PasswordMatcher;
import com.goaltracker.user.application.port.TokenGenerator;
import com.goaltracker.user.domain.Role;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.domain.SkillsEnum;
import com.goaltracker.user.domain.exception.InvalidCredentialsException;
import com.goaltracker.user.domain.exception.UserAlreadyInactiveException;
import com.goaltracker.user.domain.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthenticateUser Use Case - unit tests")
class AuthenticateUserTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordMatcher passwordMatcher;

    @Mock
    TokenGenerator tokenGenerator;

    @Captor
    ArgumentCaptor<UUID> publicIdCaptor;

    @Captor
    ArgumentCaptor<String> usernameCaptor;

    @Captor
    ArgumentCaptor<Role> roleCaptor;

    // Não usamos @InjectMocks porque AuthenticateUser é um record; instanciamos explicitamente em cada teste.
    private AuthenticateUser subject() {
        return new AuthenticateUser(userRepository, passwordMatcher, tokenGenerator);
    }

    @Test
    @DisplayName("Sucesso: autentica usuário e retorna AuthResult")
    void shouldAuthenticateAndReturnTokenWhenCredentialsAreValid() {
        // Arrange
        UUID publicId = UUID.randomUUID();
        String username = "joao";
        String rawPassword = "P@ssw0rd!";
        String storedPassword = "hashedPassword"; // valor salvo no user.getPassword()

        User user = new User(publicId, username, "joao@example.com", Set.of(), storedPassword, Instant.now());
        LoginCommand cmd = new LoginCommand(username, rawPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordMatcher.matches(rawPassword, storedPassword)).thenReturn(true);

        AuthResult expected = mock(AuthResult.class);
        when(tokenGenerator.generate(publicId, username, user.getRole())).thenReturn(expected);

        AuthenticateUser usecase = subject();

        // Act
        AuthResult result = usecase.execute(cmd);

        // Assert
        assertNotNull(result, "AuthResult não deve ser nulo");
        assertSame(expected, result, "Deve retornar o AuthResult gerado pelo TokenGenerator");

        // Verifica que passwordMatcher foi invocado corretamente
        verify(passwordMatcher, times(1)).matches(rawPassword, storedPassword);

        // Verifica que tokenGenerator foi invocado com os argumentos corretos
        verify(tokenGenerator, times(1)).generate(publicIdCaptor.capture(), usernameCaptor.capture(), roleCaptor.capture());
        assertEquals(publicId, publicIdCaptor.getValue(), "publicId passado para tokenGenerator deve ser o publicId do usuário");
        assertEquals(username, usernameCaptor.getValue(), "username passado para tokenGenerator deve ser o username do usuário");
        assertEquals(user.getRole(), roleCaptor.getValue(), "role passado para tokenGenerator deve ser o role do usuário");
    }

    @Test
    @DisplayName("Falha: usuário não encontrado -> UserNotFoundException")
    void shouldThrowUserNotFoundWhenUserDoesNotExist() {
        // Arrange
        String username = "naoexiste";
        LoginCommand cmd = new LoginCommand(username, "qualquer");

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        AuthenticateUser usecase = subject();

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> usecase.execute(cmd));

        // Verifica que não houve chamadas ao matcher/tokenGenerator
        verifyNoInteractions(passwordMatcher);
        verifyNoInteractions(tokenGenerator);
    }

    @Test
    @DisplayName("Falha: usuário inativo -> UserAlreadyInactiveException")
    void shouldThrowWhenUserIsInactive() {
        // Arrange
        UUID publicId = UUID.randomUUID();
        String username = "maria";
        String rawPassword = "P@ssw0rd!";
        User user = new User(publicId, username, "maria@example.com", Set.of(), "storedHash", Instant.now());

        // desativar o usuário para simular inatividade
        user.deactivate();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        AuthenticateUser usecase = subject();

        // Act & Assert
        assertThrows(UserAlreadyInactiveException.class, () -> usecase.execute(new LoginCommand(username, rawPassword)));

        // Verifica que não houve tentativa de checar senha nem geração de token
        verifyNoInteractions(passwordMatcher);
        verifyNoInteractions(tokenGenerator);
    }

    @Test
    @DisplayName("Falha: senha incorreta -> InvalidCredentialsException e token não gerado")
    void shouldThrowInvalidCredentialsWhenPasswordDoesNotMatch() {
        // Arrange
        UUID publicId = UUID.randomUUID();
        String username = "carlos";
        String rawPassword = "wrongPassword";
        String storedPassword = "storedHash";
        User user = new User(publicId, username, "carlos@example.com", Set.of(), storedPassword, Instant.now());

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordMatcher.matches(rawPassword, storedPassword)).thenReturn(false);

        AuthenticateUser usecase = subject();

        // Act & Assert
        InvalidCredentialsException ex = assertThrows(InvalidCredentialsException.class,
                () -> usecase.execute(new LoginCommand(username, rawPassword)));

        assertTrue(ex.getMessage().toLowerCase().contains("invalid"), "mensagem de exceção deve indicar credenciais inválidas");

        verify(tokenGenerator, never()).generate(any(), anyString(), any());
    }
}