package com.mroh.modularmonolith.user.web.controller;

import com.mroh.modularmonolith.user.web.dto.CreateUserRequest;
import com.mroh.modularmonolith.user.web.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createUser_shouldReturnCreatedUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("integrationtestuser");
        request.setEmail("integrationtest@example.com");

        ResponseEntity<UserResponse> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/users", request, UserResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserResponse response = responseEntity.getBody();
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getUsername()).isEqualTo("integrationtestuser");
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        // Create a user first
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("user1");
        request.setEmail("user1@example.com");
        restTemplate.postForEntity("http://localhost:" + port + "/users", request, UserResponse.class);

        // Get all users
        ResponseEntity<UserResponse[]> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/users", UserResponse[].class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserResponse[] users = responseEntity.getBody();
        assertThat(users).isNotNull();
        assertThat(users.length).isGreaterThanOrEqualTo(1);
    }
}
