package com.mroh.modularmonolith.user.application.command;

import com.mroh.modularmonolith.user.domain.model.User;
import com.mroh.modularmonolith.user.domain.repository.UserRepository;
import com.mroh.modularmonolith.user.domain.event.UserCreatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateUserCommandHandlerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CreateUserCommandHandler commandHandler;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handle_shouldSaveUserAndPublishEvent() {
        CreateUserCommand command = new CreateUserCommand("testuser", "test@example.com");
        User savedUser = new User(1L, "testuser", "test@example.com");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = commandHandler.handle(command);

        assertThat(result.getId()).isEqualTo(1L);
        verify(userRepository, times(1)).save(any(User.class));
        verify(eventPublisher, times(1)).publishEvent(any(UserCreatedEvent.class));
    }
}
