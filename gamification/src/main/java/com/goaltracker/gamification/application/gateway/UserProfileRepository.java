package com.goaltracker.gamification.application.gateway;

import com.goaltracker.gamification.domain.UserProfile;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository {
    Optional<UserProfile> findById(UUID userId);
    UserProfile save(UserProfile userProfile);
}
