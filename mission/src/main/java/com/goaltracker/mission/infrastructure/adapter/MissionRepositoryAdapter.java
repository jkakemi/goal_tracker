package com.goaltracker.mission.infrastructure.adapter;

import com.goaltracker.mission.application.gateway.MissionRepository;
import com.goaltracker.mission.domain.Mission;
import com.goaltracker.mission.infrastructure.MissionMapper;
import com.goaltracker.mission.infrastructure.persistence.MissionEntity;
import com.goaltracker.mission.infrastructure.persistence.MissionRepositoryJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Page<Mission> findAll(Pageable pageable){
        return missionRepositoryJpa.findAll(pageable)
                .map(missionMapper::toDomain);
    }

    @Override
    public Optional<Mission> findById(Long id) {
        return missionRepositoryJpa.findById(id)
                .map(missionMapper::toDomain);
    }

    @Override
    public Page<Mission> findAllByUserId(UUID userId, Pageable pageable) {
        return missionRepositoryJpa.findAllByUserId(userId, pageable).map(missionMapper::toDomain);
    }
}
