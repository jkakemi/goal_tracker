package com.goaltracker.user.infrastructure;


import com.goaltracker.user.domain.SkillsEnum;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.infrastructure.persistence.UserEntity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class UserMapper {

    public UserEntity toEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getXpTotal(),
                user.getLevel(),
                user.getSkills().toString(),
                user.isActive(),
                user.getCreated_at(),
                user.getUpdated_at(),
                user.getUpdated_at()
        );
    }

    public User toDomain(UserEntity entity) {
        Set<SkillsEnum> skills = new HashSet<>();
        return new User(
                entity.getPublicId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getXpTotal(),
                entity.getLevel(),
                skills,
                entity.isActive(),
                entity.getCreated_at(),
                entity.getUpdated_at(),
                entity.getUpdated_at()
        );
    }

    public void updateEntity(UserEntity entity, User user) {
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setActive(user.isActive());
        entity.setDeleted_at(user.getDeleted_at());
        entity.setUpdated_at(user.getUpdated_at());
        entity.setSkills(user.getSkills().toString());
    }
}
