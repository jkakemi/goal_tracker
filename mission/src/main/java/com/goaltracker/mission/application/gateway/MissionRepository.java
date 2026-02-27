package com.goaltracker.mission.application.gateway;

import com.goaltracker.mission.domain.Mission;

public interface MissionRepository {
    Mission save(Mission mission);
    boolean existsByTitle(String title);
}
