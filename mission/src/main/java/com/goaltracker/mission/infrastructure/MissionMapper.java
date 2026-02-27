package com.goaltracker.mission.infrastructure;

import com.goaltracker.mission.domain.Mission;
import com.goaltracker.mission.infrastructure.persistence.MissionEntity;
import org.springframework.stereotype.Component;

@Component
public class MissionMapper {
    public MissionEntity toEntity(Mission domain) {
        if (domain == null) {
            return null;
        }

        return new MissionEntity(
                domain.getId(),
                domain.getUserId(),
                domain.getTitle(),
                domain.getCategory(),
                domain.getDifficulty(),
                domain.getDeadline(),
                domain.getStatus(),
                domain.getCreatedAt(),
                domain.getUpdatedAt(),
                domain.getDeletedAt()
        );
    }

    public Mission toDomain(MissionEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Mission(
                entity.getId(),
                entity.getUserId(),
                entity.getTitle(),
                entity.getCategory(),
                entity.getDifficulty(),
                entity.getDeadline(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }
}
