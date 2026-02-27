package com.goaltracker.mission.infrastructure.adapter;

import com.goaltracker.mission.application.gateway.MissionRepository;
import com.goaltracker.mission.domain.Mission;
import com.goaltracker.mission.infrastructure.MissionMapper;
import com.goaltracker.mission.infrastructure.persistence.MissionEntity;
import com.goaltracker.mission.infrastructure.persistence.MissionRepositoryJpa;
import org.springframework.stereotype.Component;

@Component
public class MissionRepositoryAdapter implements MissionRepository {

    private final MissionRepositoryJpa missionRepositoryJpa;
    private final MissionMapper missionMapper;

    public MissionRepositoryAdapter(MissionRepositoryJpa missionRepositoryJpa, MissionMapper missionMapper) {
        this.missionRepositoryJpa = missionRepositoryJpa;
        this.missionMapper = missionMapper;
    }

    @Override
    public Mission save(Mission mission) {
        MissionEntity missionEntity = missionMapper.toEntity(mission);
        MissionEntity savedMissionEntity = missionRepositoryJpa.save(missionEntity);
        return missionMapper.toDomain(savedMissionEntity);
    }

    @Override
    public boolean existsByTitle(String title){
        return missionRepositoryJpa.existsByTitle(title);
    }
}
