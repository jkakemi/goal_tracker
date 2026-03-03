package com.goaltracker.mission.application.gateway;

import com.goaltracker.mission.domain.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface MissionRepository {
    Mission save(Mission mission);
    boolean existsByTitle(String title);
    Page<Mission> findAll(Pageable pageable);
    Optional<Mission> findById(Long id);
    Page<Mission> findAllByUserId(UUID userId, Pageable pageable);
}
