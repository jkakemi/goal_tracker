package com.goaltracker.user.domain;

import com.goaltracker.user.domain.exception.FieldsNullException;
import com.goaltracker.user.domain.exception.InvalidEmailException;
import com.goaltracker.user.domain.exception.PasswordInvalidException;
import com.goaltracker.user.domain.exception.UserAlreadyInactiveException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class User {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private double xpTotal;
    private int level;
    private List<SkillsEnum> skills;
    private boolean active;

    public User(UUID id, String username, String email, String password, double xpTotal,
                int level, List<SkillsEnum> skills, boolean active) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.xpTotal = xpTotal;
        this.level = level;
        this.skills = skills;
        this.active = active;
    }

    public User(UUID id, String username, String email, List<SkillsEnum> skills, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.skills = skills;
        this.password = password;
        this.active = true;
    }

    public static User create(String username, String email, String password, List<SkillsEnum> skills) {
        validateEmail(email);
        validatePassword(password);

        return new User(UUID.randomUUID(),
                normalizeEmail(email),
                email,
                skills,
                password);
    }

    public void deactivate() {
        if (!this.active) {
            throw new UserAlreadyInactiveException();
        }
        this.active = false;
    }

    private static void validatePassword(String password) {
        if (password == null || password.isBlank() || password.length() < 8) {
            throw new PasswordInvalidException();
        }

        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[@$!%*?&.#_-].*");

        if (!(hasLower && hasUpper && hasDigit && hasSpecial)) {
            throw new PasswordInvalidException();
        }
    }

    private static void validateEmail(String email) {
        if (email == null || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new InvalidEmailException();
        }
    }

    private static void validateNotNullFields(String email, String username){
        if (email == null || username == null){
            throw new FieldsNullException();
        }
    }

    private static String normalizeEmail(String email) {
        return email.toLowerCase().trim();
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public double getXpTotal() {
        return xpTotal;
    }

    public int getLevel() {
        return level;
    }

    public List<SkillsEnum> getSkills() {
        return skills;
    }
}
