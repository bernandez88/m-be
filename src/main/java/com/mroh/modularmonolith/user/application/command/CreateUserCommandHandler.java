package com.mroh.modularmonolith.user.application.command;

import com.mroh.modularmonolith.user.domain.event.UserCreatedEvent;
import com.mroh.modularmonolith.user.domain.model.User;
import com.mroh.modularmonolith.user.domain.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CreateUserCommandHandler {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CreateUserCommandHandler(UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public User handle(CreateUserCommand command) {
        if (command.getUsername() == null || command.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        User user = new User();
        user.setUsername(command.getUsername());
        user.setEmail(command.getEmail());
        User savedUser = userRepository.save(user);
        eventPublisher.publishEvent(new UserCreatedEvent(savedUser));
        return savedUser;
    }
}
