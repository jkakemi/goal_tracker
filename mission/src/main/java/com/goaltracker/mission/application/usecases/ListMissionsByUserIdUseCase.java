package com.goaltracker.mission.application.usecases;

import com.goaltracker.mission.application.gateway.MissionRepository;
import com.goaltracker.mission.domain.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class ListMissionsByUserIdUseCase {
    private final MissionRepository missionRepository;

    public ListMissionsByUserIdUseCase(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    public Page<Mission> execute(UUID userId, Pageable pageable) {
        return missionRepository.findAllByUserId(userId, pageable);
    }
}
