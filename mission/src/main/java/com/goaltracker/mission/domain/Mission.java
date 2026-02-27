package com.goaltracker.mission.domain;

import com.goaltracker.mission.domain.exception.DomainException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class Mission {
    private Long id;
    private UUID userId;
    private String title;
    private Category category;
    private Integer difficulty;
    private LocalDateTime deadline;
    private StatusMission status;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    public Mission(Long id, UUID userId, String title, Category category, Integer difficulty, LocalDateTime deadline, StatusMission status, Instant createdAt, Instant updatedAt, Instant deletedAt) {
        if(deadline != null & deadline.isBefore(LocalDateTime.now())){
            throw new DomainException("O prazo final da missão não pode estar no passado.");
        }
        if(difficulty == null || difficulty < 1 || difficulty > 5){
            throw new DomainException("A dificuldade deve ser um valor entre 1 e 5.");
        }
        if(title == null || title.isBlank()){
            throw new DomainException("O nome não pode estar vazio.");
        }

        this.id = id;
        this.userId = userId;
        this.title = title;
        this.category = category;
        this.difficulty = difficulty;
        this.deadline = deadline;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;

    }

    public Long getId() {
        return id;
    }

    public UUID getUserId() {
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

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public StatusMission getStatus() {
        return status;
    }

    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public Instant getDeletedAt() { return deletedAt; }

    // criar as regras de negócio na domain futuramente

    // exemplo de regra:
    public void complete() {
        if (this.status == StatusMission.CANCELADA) {
            throw new IllegalArgumentException("Uma missão cancelada não pode ser concluída.");
        }
        this.status = StatusMission.CONCLUIDA;
        this.updatedAt = Instant.now();
    }


}
