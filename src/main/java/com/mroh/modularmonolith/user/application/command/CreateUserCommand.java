package com.mroh.modularmonolith.user.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserCommand {
    private String username;
    private String email;
}
