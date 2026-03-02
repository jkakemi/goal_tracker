package com.goaltracker.mission.config;

import com.goaltracker.mission.application.gateway.MissionRepository;
import com.goaltracker.mission.application.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MissionConfig {
    @Bean
    public CreateMissionUseCase createMissionUseCase(MissionRepository missionRepository){
        return new CreateMissionUseCase(missionRepository);
    }

    @Bean
    public ListMissionUserCase listMissionUserCase(MissionRepository missionRepository){
        return new ListMissionUserCase(missionRepository);
    }

    @Bean
    public ListMissionsByUserIdUseCase listMissionsByUserIdCase(MissionRepository missionRepository){
        return new ListMissionsByUserIdUseCase(missionRepository);
    }

    @Bean
    public CompleteMissionUseCase completeMissionUseCase(MissionRepository missionRepository) {
        return new CompleteMissionUseCase(missionRepository);
    }

    @Bean
    public UpdateMissionUseCase updateMissionUseCase(MissionRepository missionRepository) {
        return new UpdateMissionUseCase(missionRepository);
    }

    @Bean
    public DeleteMissionUseCase deleteMissionUseCase(MissionRepository missionRepository) {
        return new DeleteMissionUseCase(missionRepository);
    }
}
