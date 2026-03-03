package com.goaltracker.mission.application.usecases;

import com.goaltracker.mission.application.command.CreateMissionCommand;
import com.goaltracker.mission.application.gateway.MissionRepository;
import com.goaltracker.mission.domain.Mission;
import com.goaltracker.mission.domain.StatusMission;
import com.goaltracker.mission.domain.exception.DomainException;

import java.util.UUID;

public class CreateMissionUseCase {

    private final MissionRepository missionRepository;

    public CreateMissionUseCase(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    public Mission execute(CreateMissionCommand command) {
        if(missionRepository.existsByTitle(command.title())){
            throw new DomainException("Já existe uma missão cadastrada com este título");
        }

        UUID userIdTest = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        Mission newMission = new Mission(
                null,
                userIdTest,
                command.title(),
                command.category(),
                command.difficulty(),
                command.deadline(),
                StatusMission.PENDENTE,
                null,
                null,
                null
        );

        return missionRepository.save(newMission);
    }

}
