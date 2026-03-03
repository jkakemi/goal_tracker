package com.goaltracker.mission.application.usecases;

import com.goaltracker.mission.application.gateway.MissionRepository;
import com.goaltracker.mission.domain.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ListMissionUserCase {
    private final MissionRepository missionRepository;

    public ListMissionUserCase(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    public Page<Mission> execute(Pageable pageable) {
        return missionRepository.findAll(pageable);
    }

}
