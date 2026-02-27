package com.goaltracker.mission.infrastructure.adapter;

import com.goaltracker.mission.application.command.CreateMissionCommand;
import com.goaltracker.mission.application.usecases.CreateMissionUseCase;
import com.goaltracker.mission.domain.Mission;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/missions")
public class MissionController {

    private final CreateMissionUseCase createMissionUseCase;

    public MissionController(CreateMissionUseCase createMissionUseCase){
        this.createMissionUseCase = createMissionUseCase;
    }

    @PostMapping
    public ResponseEntity<MissionResponse> createMission(@RequestBody @Valid CreateMissionCommand command){
        Mission createdMission = createMissionUseCase.execute(command);

        MissionResponse response = new MissionResponse(
                createdMission.getId(),
                createdMission.getUserId(),
                createdMission.getTitle(),
                createdMission.getCategory(),
                createdMission.getDifficulty(),
                createdMission.getDeadline(),
                createdMission.getStatus(),
                createdMission.getCreatedAt()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
