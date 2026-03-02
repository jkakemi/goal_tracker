package com.goaltracker.mission.application.usecases;

import com.goaltracker.mission.application.gateway.MissionRepository;
import com.goaltracker.mission.domain.Mission;
import com.goaltracker.mission.domain.exception.DomainException;

public class CompleteMissionUseCase {
    private final MissionRepository missionRepository;

    public CompleteMissionUseCase(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    public Mission execute(Long id){
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new DomainException("Missão não encontrada com o ID: " + id));

        mission.complete();
        return missionRepository.save(mission);
    }
}
