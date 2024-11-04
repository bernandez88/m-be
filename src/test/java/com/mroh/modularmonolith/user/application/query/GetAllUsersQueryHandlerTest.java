package com.mroh.modularmonolith.user.application.query;

import com.mroh.modularmonolith.user.domain.model.User;
import com.mroh.modularmonolith.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GetAllUsersQueryHandlerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetAllUsersQueryHandler queryHandler;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handle_shouldReturnAllUsers() {
        List<User> users = Arrays.asList(
            new User(1L, "user1", "user1@example.com"),
            new User(2L, "user2", "user2@example.com")
        );

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = queryHandler.handle(new GetAllUsersQuery());

        assertThat(result).hasSize(2);
        verify(userRepository, times(1)).findAll();
    }
}
