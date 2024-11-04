package com.mroh.modularmonolith.user.web.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class CreateUserRequest {

    @NotEmpty(message = "Username is required")
    @Schema(description = "Username of the new user", example = "john_doe")
    private String username;

    @Email(message = "Email should be valid")
    @Schema(description = "Email address of the new user", example = "john@example.com")
    private String email;
}
