package com.goaltracker.mission.application.usecases;

import com.goaltracker.mission.application.gateway.MissionRepository;
import com.goaltracker.mission.domain.Mission;
import com.goaltracker.mission.domain.exception.DomainException;

public class DeleteMissionUseCase {
    private final MissionRepository missionRepository;

    public DeleteMissionUseCase(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    public void execute(Long id) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new DomainException("Missão não encontrada."));
        mission.delete();
        missionRepository.save(mission);
    }
}
