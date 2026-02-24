package com.goaltracker.user.domain;

import java.util.List;

public class User {
    private String username;
    private String password;
    private double xpTotal;
    private int level;
    private List<SkillsEnum> skills;

    public User(String username, String password, List<SkillsEnum> skills) {
        this.username = username;
        this.xpTotal = 0;
        this.level = 0;
        this.skills = skills;
        if (!validatorPassword(password)){
            throw new RuntimeException("Password must contain at least 8 characters,\n" +
                    "including uppercase, lowercase,\n" +
                    "number and special character. ");
        }
        this.password = password;
    }

    private boolean validatorPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[@$!%*?&.#_-].*");

        return hasLower && hasUpper && hasDigit && hasSpecial;
    }

    public String getUsername() {
        return username;
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
