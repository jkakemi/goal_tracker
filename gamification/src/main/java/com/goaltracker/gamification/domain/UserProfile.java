package com.goaltracker.gamification.domain;

import java.util.UUID;

public class UserProfile {
    private Long id;
    private UUID userId;
    private Integer xp;
    private Integer level;

    public UserProfile(Long id, UUID userId, Integer xp, Integer level) {
        this.id = id;
        this.userId = userId;
        this.xp = (xp != null) ? xp : 0;
        this.level = (level != null) ? level : 1;
    }

    public void addXp(int pointsEarned) {
        if(pointsEarned < 0){
            throw new  IllegalArgumentException("Os pontos ganhos não podem ser negativos.");
        }
        this.xp += pointsEarned;
        calculateLevel();
    }

    private void calculateLevel() {
        this.level = (this.xp/1000)+1;
    }

    public Long getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public Integer getXp() {
        return xp;
    }

    public Integer getLevel() {
        return level;
    }
}


