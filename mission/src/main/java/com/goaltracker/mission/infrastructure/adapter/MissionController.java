package com.goaltracker.mission.infrastructure.adapter;

import com.goaltracker.mission.application.command.CreateMissionCommand;
import com.goaltracker.mission.application.command.UpdateMissionCommand;
import com.goaltracker.mission.application.usecases.*;
import com.goaltracker.mission.domain.Mission;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/missions")
public class MissionController {

    private final CreateMissionUseCase createMissionUseCase;
    private final ListMissionUserCase listMissionUserCase;
    private final ListMissionsByUserIdUseCase listMissionsByUserIdCase;
    private final CompleteMissionUseCase completeMissionUseCase;
    private final UpdateMissionUseCase updateMissionUseCase;
    private final DeleteMissionUseCase deleteMissionUseCase;

    public MissionController(CreateMissionUseCase createMissionUseCase, ListMissionUserCase listMissionUserCase,
                             ListMissionsByUserIdUseCase listMissionsByUserIdCase, CompleteMissionUseCase completeMissionUseCase,
                             UpdateMissionUseCase updateMissionUseCase, DeleteMissionUseCase deleteMissionUseCase) {
        this.createMissionUseCase = createMissionUseCase;
        this.listMissionUserCase = listMissionUserCase;
        this.listMissionsByUserIdCase = listMissionsByUserIdCase;
        this.completeMissionUseCase = completeMissionUseCase;
        this.updateMissionUseCase = updateMissionUseCase;
        this.deleteMissionUseCase = deleteMissionUseCase;
    }

    @PostMapping("/create")
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

    @GetMapping("/listAll")
    public ResponseEntity<Page<MissionResponse>> listMissions(@PageableDefault(size = 10) Pageable pageable){
        Page<Mission> missionPage = listMissionUserCase.execute(pageable);

        Page<MissionResponse> responsePage = missionPage.map(mission -> new MissionResponse(
                mission.getId(),
                mission.getUserId(),
                mission.getTitle(),
                mission.getCategory(),
                mission.getDifficulty(),
                mission.getDeadline(),
                mission.getStatus(),
                mission.getCreatedAt()
        ));

        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<MissionResponse>> listByUserId(
            @PathVariable UUID userId,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<Mission> missionsPage = listMissionsByUserIdCase.execute(userId, pageable);

        Page<MissionResponse> responsePage = missionsPage.map(mission -> new MissionResponse(
                mission.getId(), mission.getUserId(), mission.getTitle(),
                mission.getCategory(), mission.getDifficulty(), mission.getDeadline(),
                mission.getStatus(), mission.getCreatedAt()
        ));

        return ResponseEntity.ok(responsePage);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<MissionResponse> completeMission(@PathVariable Long id) {

        Mission completedMission = completeMissionUseCase.execute(id);

        MissionResponse response = new MissionResponse(
                completedMission.getId(), completedMission.getUserId(), completedMission.getTitle(),
                completedMission.getCategory(), completedMission.getDifficulty(), completedMission.getDeadline(),
                completedMission.getStatus(), completedMission.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MissionResponse> updateMission(@PathVariable Long id, @RequestBody UpdateMissionCommand command) {
        Mission updatedMission = updateMissionUseCase.execute(id, command);

        MissionResponse response = new MissionResponse(
                updatedMission.getId(), updatedMission.getUserId(), updatedMission.getTitle(),
                updatedMission.getCategory(), updatedMission.getDifficulty(), updatedMission.getDeadline(),
                updatedMission.getStatus(), updatedMission.getCreatedAt()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMission(@PathVariable Long id) {
        deleteMissionUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }



}
