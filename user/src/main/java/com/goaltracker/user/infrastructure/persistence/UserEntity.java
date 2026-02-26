package com.goaltracker.user.infrastructure.persistence;

import com.goaltracker.user.domain.SkillsEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_users_public_id", columnNames = "public_id"),
                @UniqueConstraint(name = "uk_users_cpf", columnNames = "cpf"),
                @UniqueConstraint(name = "uk_users_email", columnNames = "email")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(
            name = "users_seq",
            sequenceName = "users_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "public_id", nullable = false, updatable = false, unique = true)
    private UUID publicId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    private double xpTotal;
    private int level;
    private String skills;
    private boolean active;
    private Instant created_at;
    private Instant updated_at;
    private Instant deleted_at;

    public UserEntity(UUID publicId, String username, String email, String password,
                      double xpTotal, int level, String skills, boolean active,
                      Instant created_at, Instant updated_at, Instant deleted_at) {
        this.publicId = publicId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.xpTotal = xpTotal;
        this.level = level;
        this.skills = skills;
        this.active = active;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    @PrePersist
    private void prePersist() {
        if (this.publicId == null) {
            this.publicId = UUID.randomUUID();
        }
        if (this.created_at == null) {
            this.created_at = Instant.now();
        }
    }
}
