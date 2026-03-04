package com.goaltracker.gamification.infrastructure.persistence;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user_profiles")
public class UserProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true, updatable = false)
    private UUID userId;

    @Column(nullable = false)
    private Integer xp;

    @Column(name = "user_level", nullable = false)
    private Integer level;

    public UserProfileEntity() {}

    public UserProfileEntity(Long id, UUID userId, Integer xp, Integer level) {
        this.id = id;
        this.userId = userId;
        this.xp = xp;
        this.level = level;
    }

    public Long getId() { return id; }
    public UUID getUserId() { return userId; }
    public Integer getXp() { return xp; }
    public Integer getLevel() { return level; }
}
