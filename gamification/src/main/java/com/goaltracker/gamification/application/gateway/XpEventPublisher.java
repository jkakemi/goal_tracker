package com.goaltracker.gamification.application.gateway;

import com.goaltracker.gamification.infrastructure.adapter.event.XpAwardedEvent;

public interface XpEventPublisher {
    void publish(XpAwardedEvent event);
}
