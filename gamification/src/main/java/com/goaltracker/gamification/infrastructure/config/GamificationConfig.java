package com.goaltracker.gamification.infrastructure.config;

import com.goaltracker.gamification.application.gateway.XpEventPublisher;
import com.goaltracker.gamification.application.usecases.AddXpUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GamificationConfig {
    @Bean
    public AddXpUseCase addXpUseCase(XpEventPublisher xpEventPublisher) {
        return new AddXpUseCase(xpEventPublisher);
    }
}
