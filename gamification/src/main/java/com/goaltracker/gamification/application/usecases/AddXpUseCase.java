package com.goaltracker.gamification.application.usecases;

import com.goaltracker.gamification.application.gateway.UserProfileRepository;
import com.goaltracker.gamification.domain.UserProfile;

import java.util.UUID;

public class AddXpUseCase {
    private final UserProfileRepository userProfileRepository;

    public AddXpUseCase(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public void execute(UUID userId, Integer difficulty) {
        int xpEarned = difficulty*100;
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElse(new UserProfile(null, userId, 0, 1));

        userProfile.addXp(xpEarned);
        userProfileRepository.save(userProfile);
        System.out.println("⭐ " + xpEarned + " XP adicionados! Nível atual: " + userProfile.getLevel());
    }
}
