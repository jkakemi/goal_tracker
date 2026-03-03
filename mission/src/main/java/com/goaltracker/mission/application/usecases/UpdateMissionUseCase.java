package com.goaltracker.mission.application.usecases;

import com.goaltracker.mission.application.command.UpdateMissionCommand;
import com.goaltracker.mission.application.gateway.MissionRepository;
import com.goaltracker.mission.domain.Mission;

public class UpdateMissionUseCase {
    private final MissionRepository missionRepository;

    public UpdateMissionUseCase(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    public Mission execute(Long id, UpdateMissionCommand command) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada"));

        mission.update(
                command.title(),
                command.category(),
                command.difficulty(),
                command.deadline()
        );
        return missionRepository.save(mission);
    }
}
