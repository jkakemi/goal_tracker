package com.goaltracker.mission.config;

import com.goaltracker.mission.application.gateway.MissionRepository;
import com.goaltracker.mission.application.usecases.CreateMissionUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MissionConfig {
    @Bean
    public CreateMissionUseCase createMissionUseCase(MissionRepository missionRepository){
        return new CreateMissionUseCase(missionRepository);
    }
}
