package com.goaltracker.mission.application.usecases;

import com.goaltracker.mission.application.event.MissionCompletedEvent;
import com.goaltracker.mission.application.gateway.MissionEventPublisher;
import com.goaltracker.mission.application.gateway.MissionRepository;
import com.goaltracker.mission.domain.Mission;
import com.goaltracker.mission.domain.exception.DomainException;

public class CompleteMissionUseCase {
    private final MissionRepository missionRepository;
    private final MissionEventPublisher eventPublisher;

    public CompleteMissionUseCase(MissionRepository missionRepository, MissionEventPublisher eventPublisher) {
        this.missionRepository = missionRepository;
        this.eventPublisher = eventPublisher;
    }

    public Mission execute(Long id){
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new DomainException("Missão não encontrada com o ID: " + id));

        mission.complete();
        Mission savedMission = missionRepository.save(mission);
        MissionCompletedEvent event = new MissionCompletedEvent(
                savedMission.getUserId(),
                savedMission.getCategory(),
                savedMission.getDifficulty()
        );

        eventPublisher.publishMissionCompleted(event);
        return savedMission;
    }
}
