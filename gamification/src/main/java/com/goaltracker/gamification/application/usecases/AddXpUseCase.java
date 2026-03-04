package com.goaltracker.gamification.application.usecases;

import com.goaltracker.gamification.application.gateway.XpEventPublisher;
import com.goaltracker.gamification.infrastructure.adapter.event.XpAwardedEvent;

import java.util.UUID;

public class AddXpUseCase {

    private final XpEventPublisher xpEventPublisher;

    public AddXpUseCase(XpEventPublisher xpEventPublisher) {
        this.xpEventPublisher = xpEventPublisher;
    }

    public void execute(UUID userId, Integer difficulty) {

        int xpEarned = difficulty * 100;
        XpAwardedEvent event = new XpAwardedEvent(userId, xpEarned);
        xpEventPublisher.publish(event);
    }
}