package com.mroh.modularmonolith.user.domain.event;

import com.mroh.modularmonolith.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreatedEvent {
    private User user;
}
