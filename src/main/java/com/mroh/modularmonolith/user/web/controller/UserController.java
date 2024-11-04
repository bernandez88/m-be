package com.mroh.modularmonolith.user.web.controller;

import com.mroh.modularmonolith.user.application.command.CreateUserCommand;
import com.mroh.modularmonolith.user.application.command.CreateUserCommandHandler;
import com.mroh.modularmonolith.user.application.query.GetAllUsersQuery;
import com.mroh.modularmonolith.user.application.query.GetAllUsersQueryHandler;
import com.mroh.modularmonolith.user.domain.model.User;
import com.mroh.modularmonolith.user.web.dto.CreateUserRequest;
import com.mroh.modularmonolith.user.web.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "Operations related to user management")
public class UserController {

    private final CreateUserCommandHandler createUserCommandHandler;
    private final GetAllUsersQueryHandler getAllUsersQueryHandler;

    public UserController(CreateUserCommandHandler createUserCommandHandler, GetAllUsersQueryHandler getAllUsersQueryHandler) {
       this.createUserCommandHandler = createUserCommandHandler;
       this.getAllUsersQueryHandler = getAllUsersQueryHandler;
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the given information")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        CreateUserCommand command = new CreateUserCommand(request.getUsername(), request.getEmail());
        User createdUser = createUserCommandHandler.handle(command);
        UserResponse response = new UserResponse(createdUser.getId(), createdUser.getUsername(), createdUser.getEmail());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        GetAllUsersQuery query = new GetAllUsersQuery();
        List<User> users = getAllUsersQueryHandler.handle(query);
        List<UserResponse> response = users.stream()
            .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
