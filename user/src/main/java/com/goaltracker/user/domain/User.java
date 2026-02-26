package com.goaltracker.user.domain;

import com.goaltracker.user.domain.exception.FieldsNullException;
import com.goaltracker.user.domain.exception.InvalidEmailException;
import com.goaltracker.user.domain.exception.PasswordInvalidException;
import com.goaltracker.user.domain.exception.UserAlreadyInactiveException;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private double xpTotal;
    private int level;
    private Set<SkillsEnum> skills = new HashSet<>();
    private boolean active;
    private Instant created_at;
    private Instant updated_at;
    private Instant deleted_at;


    public User(UUID id, String username, String email, String password, double xpTotal,
                int level, Set<SkillsEnum> skills, boolean active, Instant created_at,
                Instant updated_at, Instant deleted_at) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.xpTotal = xpTotal;
        this.level = level;
        this.skills = skills;
        this.active = active;
    }

    public User(UUID id, String username, String email, Set<SkillsEnum> skills, String password, Instant created_at) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.skills = skills;
        this.password = password;
        this.active = true;
        this.created_at = created_at;
    }

    public static User create(String username, String email, String password, Set<SkillsEnum> skills) {
        validateNotNullFields(email, username);
        validateEmail(email);
        validatePassword(password);

        return new User(UUID.randomUUID(),
                normalizeEmail(email),
                email,
                skills,
                password,
                Instant.now());
    }

    public void deactivate() {
        if (!this.active) {
            throw new UserAlreadyInactiveException();
        }
        this.active = false;
        this.deleted_at = Instant.now();
    }

    public boolean isActive(){
        return active;
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

    public void updateEmail(String email) {
        validateEmail(email);
        this.email = normalizeEmail(email);

    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updatedAtNew() {
        this.updated_at = Instant.now();
    }

    public void addSkills(Set<SkillsEnum> skillsToAdd) {
        this.skills.addAll(skillsToAdd);
    }

    public void removeSkills(Set<SkillsEnum> skillsToRemove) {
        this.skills.removeAll(skillsToRemove);
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

    public Set<SkillsEnum> getSkills() {
        return skills;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public Instant getUpdated_at() {
        return updated_at;
    }

    public Instant getDeleted_at() {
        return deleted_at;
    }
}
