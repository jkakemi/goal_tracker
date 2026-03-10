package com.goaltracker.user.domain;

import com.goaltracker.user.domain.exception.FieldsNullException;
import com.goaltracker.user.domain.exception.InvalidEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Cria usuário com dados válidos")
    void shouldCreateUserWhenValidInputs() {
        String username = "joao";
        String email = "joao@example.com";
        String password = "P@ssw0rd!";
        Set<SkillsEnum> skills = Set.<SkillsEnum>of();
        User user = User.create(username, email, password, skills);

        assertNotNull(user, "User não deve ser nulo");
        assertNotNull(user.getPublicId(), "publicId deve ser gerado");
        assertInstanceOf(UUID.class, user.getPublicId(), "publicId deve ser UUID");

        assertEquals(email.toLowerCase(), user.getEmail(), "email armazenado deve ser a versão normalizada (lowercase)");
        assertEquals(skills, user.getSkills(), "skills devem ser atribuídas corretamente");
        assertEquals(password, user.getPassword(), "password deve ser armazenada como passada");

        assertNotNull(user.getCreated_at(), "createdAt deve ser preenchido");
        Instant now = Instant.now();
        assertTrue(!user.getCreated_at().isAfter(now.plusSeconds(2)), "createdAt não deve ser no futuro");
        assertTrue(!user.getCreated_at().isBefore(now.minusSeconds(5)), "createdAt deve ser recente (não muito antigo)");
    }

    @Test
    @DisplayName("Null email lança FieldsNullException (checagem prioriza null antes de formato)")
    void shouldThrowFieldsNullExceptionWhenEmailIsNull() {
        String username = "joao";
        String password = "P@ssw0rd!";
        Set<SkillsEnum> skills = Set.<SkillsEnum>of();

        assertThrows(FieldsNullException.class,
                () -> User.create(username, null, password, skills),
                "email nulo deve lançar FieldsNullException");
    }

    @Test
    @DisplayName("Null username lança FieldsNullException")
    void shouldThrowFieldsNullExceptionWhenUsernameIsNull() {
        String email = "joao@example.com";
        String password = "P@ssw0rd!";
        Set<SkillsEnum> skills = Set.<SkillsEnum>of();

        assertThrows(FieldsNullException.class,
                () -> User.create(null, email, password, skills),
                "username nulo deve lançar FieldsNullException");
    }

    @Test
    @DisplayName("Null password lança FieldsNullException")
    void shouldThrowFieldsNullExceptionWhenPasswordIsNull() {
        String username = "joao";
        String email = "joao@example.com";
        Set<SkillsEnum> skills = Set.<SkillsEnum>of();

        assertThrows(FieldsNullException.class,
                () -> User.create(username, email, null, skills),
                "password nulo deve lançar FieldsNullException");
    }

    @Test
    @DisplayName("Email inválido por formato lança InvalidEmailException")
    void shouldThrowInvalidEmailExceptionForInvalidFormat() {
        String username = "joao";
        String badEmail = "invalid-email-without-at";
        String password = "P@ssw0rd!";
        Set<SkillsEnum> skills = Set.<SkillsEnum>of();

        assertThrows(InvalidEmailException.class,
                () -> User.create(username, badEmail, password, skills),
                "email com formato inválido deve lançar InvalidEmailException");
    }

    @Test
    @DisplayName("Email com espaços em branco é inválido e lança InvalidEmailException")
    void shouldThrowInvalidEmailExceptionForEmailWithSpaces() {
        String username = "joao";
        String badEmail = "joao @example.com";
        String password = "P@ssw0rd!";
        Set<SkillsEnum> skills = Set.<SkillsEnum>of();

        assertThrows(InvalidEmailException.class,
                () -> User.create(username, badEmail, password, skills),
                "email com espaços deve lançar InvalidEmailException");
    }

    @Test
    @DisplayName("Normalized email deve ser lowercase (assunção de normalizeEmail)")
    void normalizedEmailShouldBeLowercase() {
        String username = "ana";
        String email = "AnA@Example.COM";
        String password = "P@ssw0rd!";
        Set<SkillsEnum> skills = Set.<SkillsEnum>of();

        User user = User.create(username, email, password, skills);

        assertEquals("ana@example.com", user.getEmail(), "normalizedEmail deve ser lowercase");
    }
}