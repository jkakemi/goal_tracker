package com.goaltracker.gamification.infrastructure;

import com.goaltracker.gamification.domain.UserProfile;
import com.goaltracker.gamification.infrastructure.persistence.UserProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    public UserProfile toDomain(UserProfileEntity entity) {
        return new UserProfile(
                entity.getId(),
                entity.getUserId(),
                entity.getXp(),
                entity.getLevel()
        );
    }

    public UserProfileEntity toEntity(UserProfile domain) {
        return new UserProfileEntity(
                domain.getId(),
                domain.getUserId(),
                domain.getXp(),
                domain.getLevel()
        );
    }
}