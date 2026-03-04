package com.goaltracker.gamification.infrastructure.config;

import com.goaltracker.gamification.application.gateway.UserProfileRepository;
import com.goaltracker.gamification.application.usecases.AddXpUseCase;
import com.goaltracker.gamification.domain.UserProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GamificationConfig {
    @Bean
    public AddXpUseCase addXpUseCase(UserProfileRepository userProfileRepository) {
        return new AddXpUseCase(userProfileRepository);
    }
}
