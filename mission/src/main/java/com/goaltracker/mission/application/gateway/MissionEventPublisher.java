package com.goaltracker.mission.application.gateway;

import com.goaltracker.mission.application.event.MissionCompletedEvent;

public interface MissionEventPublisher {
    void publishMissionCompleted(MissionCompletedEvent event);
}
