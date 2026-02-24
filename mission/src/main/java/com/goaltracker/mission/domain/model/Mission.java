package com.goaltracker.mission.domain.model;

import java.time.LocalDateTime;

public class Mission {
    private Long id;
    private Long userId;
    private String title;
    private Category category;
    private Integer difficulty;
    private LocalDateTime startTime;
    private StatusMission status;

    public Mission(Long id, Long userId, String title, Category category, Integer difficulty, LocalDateTime startTime, StatusMission status) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.category = category;
        this.difficulty = difficulty;
        this.startTime = startTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public Category getCategory() {
        return category;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public StatusMission getStatus() {
        return status;
    }

    // regras de negócio a serem aplicadas posteriormente.
}
