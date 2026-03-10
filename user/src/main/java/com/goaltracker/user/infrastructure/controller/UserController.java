package com.goaltracker.user.infrastructure.controller;

import com.goaltracker.user.application.command.CreateUserCommand;
import com.goaltracker.user.application.command.UpdateSkillsCommand;
import com.goaltracker.user.application.command.UpdateUserCommand;
import com.goaltracker.user.application.usecases.*;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.infrastructure.controller.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final ListUsersUseCase listUsersUseCase;
    private final UpdateUserUsecase updateUserUsecase;
    private final UpdateSkillsUseCase updateSkillsUseCase;

    public UserController(CreateUserUseCase createUserUseCase,
                          GetUserUseCase getUserUseCase, ListUsersUseCase listUsersUseCase,
                          UpdateUserUsecase updateUserUsecase, UpdateSkillsUseCase updateSkillsUseCase) {
        this.createUserUseCase= createUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.listUsersUseCase = listUsersUseCase;
        this.updateUserUsecase = updateUserUsecase;
        this.updateSkillsUseCase = updateSkillsUseCase;
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserCreateDto dto){
        CreateUserCommand command = new CreateUserCommand(
                dto.username(), dto.email(), dto.password(), dto.skills());

        User user = createUserUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDto(user));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> get(@RequestParam String username){
        User user = getUserUseCase.execute(username);
        return ResponseEntity.status(HttpStatus.OK).body(new UserDto(user));
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<UserDto>> getAll(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable){
        Page<UserDto> page = listUsersUseCase
                .execute(pageable)
                .map(UserDto::new);

        return ResponseEntity.ok(PageResponse.from(page));
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@RequestBody UpdateUserDto dto, UUID userId){
        UpdateUserCommand command = new UpdateUserCommand(dto.username(), dto.email(), dto.password());
        User user = updateUserUsecase.execute(command, userId);

        return ResponseEntity.ok().body(new UserDto(user));
    }

    @PutMapping("/{skills}")
    public ResponseEntity<UserDto> updateSkills(@RequestBody UpdateSkillsDto dto, UUID userId){
        UpdateSkillsCommand command = new UpdateSkillsCommand(dto.add(), dto.skills() );
        User user = updateSkillsUseCase.execute(userId, command);

        return ResponseEntity.ok().body(new UserDto(user));
    }



}
