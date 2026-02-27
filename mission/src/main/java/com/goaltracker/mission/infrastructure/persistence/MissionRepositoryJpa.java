package com.goaltracker.mission.infrastructure.persistence;

import com.goaltracker.mission.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepositoryJpa extends JpaRepository<MissionEntity, Long> {
    boolean existsByTitle(String title);
}
