package com.goaltracker.mission.infrastructure.persistence;

import com.goaltracker.mission.domain.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MissionRepositoryJpa extends JpaRepository<MissionEntity, Long> {
    boolean existsByTitle(String title);
    Page<MissionEntity> findAllByUserId(UUID userId, Pageable pageable);
}
