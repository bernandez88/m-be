# Modular Monolith Application

## Introduction

This project is a Spring Boot Modular Monolith application built with Java 21 and Spring Boot 3.2.0. It demonstrates a clean architecture approach using the Command Query Responsibility Segregation (CQRS) pattern. The application includes a user management module with RESTful APIs, integrates with a PostgreSQL database, uses Flyway for database migrations, and provides interactive API documentation via Swagger UI.

## Architecture Overview

### Module Structure
The application follows a modular monolith architecture, organized into distinct layers and modules with clear responsibilities. The structure incorporates the CQRS pattern, separating the read and write operations.

- **Domain Layer**: Contains the core business logic and domain models.
  - `domain/model`: Entity classes representing the data model.
  - `domain/repository`: Repository interfaces for data access.
  - `domain/event`: Domain events for decoupled communication.
- **Application Layer**: Implements the application's use cases.
  - `application/command`: Handles commands (write operations).
  - `application/query`: Handles queries (read operations).
- **Infrastructure Layer**: Provides concrete implementations of repositories.
  - `infrastructure/repository`: JDBC implementations of repositories.
- **Web Layer**: Exposes RESTful APIs and handles HTTP requests/responses.
  - `web/controller`: REST controllers for handling requests.
  - `web/dto`: Data Transfer Objects for request and response payloads.
- **Configuration Layer**: Application-wide configurations.
  - `config`: Contains configuration classes (e.g., OpenAPI configuration).

### CQRS Pattern
The Command Query Responsibility Segregation (CQRS) pattern is implemented to separate the responsibilities of read and write operations:

- **Commands**:
  - Represent actions that change the state of the system (mutations).
  - Handled by Command Handlers in the `application/command` package.
  - Example: Creating a new user.
- **Queries**:
  - Represent requests to retrieve data without modifying the state.
  - Handled by Query Handlers in the `application/query` package.
  - Example: Retrieving all users.

This separation allows for better scalability, maintainability, and clarity in the codebase.

## Technologies Used
- Java 21
- Spring Boot 3.2.0
- Spring Modulith
- PostgreSQL
- Flyway
- Lombok
- Swagger (Springdoc OpenAPI)
- Testcontainers
- JUnit 5
- JaCoCo

## Features
- User Management: Create and retrieve user accounts.
- API Documentation: Interactive API documentation via Swagger UI.
- Database Migrations: Automated schema migrations with Flyway.
- Testing: Unit and integration tests with code coverage reports.
- CQRS Implementation: Clear separation of commands and queries.

## Prerequisites
- Java 21: Install and set as default.
- Gradle: The project uses the Gradle wrapper; having Gradle installed can be helpful.
- Docker and Docker Compose: Required for running PostgreSQL.

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://jestrada3@bitbucket.org/applaudostudios/mroh-rfs-backend.git
cd modular-monolith
```

### 2. Build the Project
Use the Gradle wrapper to build the project:
```bash
./gradlew clean build
```

### 3. Start PostgreSQL Database
Start the PostgreSQL database using Docker Compose:
```bash
docker-compose up -d
```
Note: Ensure port 5432 is available or update the port mapping in docker-compose.yml and application.yml.

### 4. Run the Application
Start the Spring Boot application:
```bash
./gradlew bootRun
```

### 5. Access the Application
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- Use this interface to explore and test the API endpoints.

### 6. Test the API
You can test the API using curl or any REST client like Postman.

Example: Create a new user
```bash
curl -X POST -H "Content-Type: application/json" \
     -d '{"username":"newuser","email":"newuser@example.com"}' \
     http://localhost:8080/users
```

Expected Response:
```json
{
  "id": 1,
  "username": "newuser",
  "email": "newuser@example.com"
}
```

Example: Get all users
```bash
curl -X GET http://localhost:8080/users
```

Expected Response:
```json
[
  {
    "id": 1,
    "username": "newuser",
    "email": "newuser@example.com"
  }
]
```

## Running Tests and Code Coverage

### 1. Run Tests
Execute all tests using Gradle:
```bash
./gradlew test
```

### 2. Generate Code Coverage Report
Generate a JaCoCo code coverage report:
```bash
./gradlew jacocoTestReport
```
The report will be generated at `build/jacocoHtml/index.html`.
Open the `index.html` file in a browser to view the coverage details.

### 3. Enforce Coverage Rules
Enforce code coverage minimum thresholds:
```bash
./gradlew jacocoTestCoverageVerification
```
This will check if the coverage meets the minimum threshold defined in build.gradle.

## Project Structure
```
modular-monolith/
├── build.gradle
├── settings.gradle
├── docker-compose.yml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/mroh/modularmonolith/
│   │   │       ├── ModularMonolithApplication.java
│   │   │       ├── config/
│   │   │       │   └── OpenAPIConfig.java
│   │   │       └── user/
│   │   │           ├── application/
│   │   │           │   ├── command/
│   │   │           │   │   ├── CreateUserCommand.java
│   │   │           │   │   └── CreateUserCommandHandler.java
│   │   │           │   └── query/
│   │   │           │       ├── GetAllUsersQuery.java
│   │   │           │       └── GetAllUsersQueryHandler.java
│   │   │           ├── domain/
│   │   │           │   ├── event/
│   │   │           │   │   └── UserCreatedEvent.java
│   │   │           │   ├── model/
│   │   │           │   │   └── User.java
│   │   │           │   └── repository/
│   │   │           │       └── UserRepository.java
│   │   │           ├── infrastructure/
│   │   │           │   └── repository/
│   │   │           │       └── JdbcUserRepository.java
│   │   │           └── web/
│   │   │               ├── controller/
│   │   │               │   └── UserController.java
│   │   │               └── dto/
│   │   │                   ├── CreateUserRequest.java
│   │   │                   └── UserResponse.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/
│   │           └── migration/
│   │               └── V1__Create_users_table.sql
│   └── test/
│       └── java/
│           └── com/mroh/modularmonolith/
│               └── user/
│                   ├── application/
│                   │   ├── command/
│                   │   │   └── CreateUserCommandHandlerTest.java
│                   │   └── query/
│                   │       └── GetAllUsersQueryHandlerTest.java
│                   └── web/
│                       └── controller/
│                           └── UserControllerIntegrationTest.java
```

## Additional Commands

Clean Build: Cleans and builds the project.
```bash
./gradlew clean build
```

Run Application with Debugging: Starts the application with remote debugging enabled.
```bash
./gradlew bootRun --debug-jvm
```

Stop PostgreSQL Container: Stops the database container.
```bash
docker-compose down
```

Check Dependencies for Updates:
```bash
./gradlew dependencyUpdates
```

## Conclusion
This project provides a foundational structure for building scalable and maintainable applications using the modular monolith architecture with CQRS pattern. It separates the read and write operations, promotes clean code practices, and leverages modern technologies.

Feel free to contribute, suggest improvements, or raise issues if you encounter any problems.

## Additional Resources
- Spring Boot Documentation: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/
- Spring Modulith: https://spring.io/projects/spring-modulith
- Swagger UI: https://swagger.io/tools/swagger-ui/
- Testcontainers: https://www.testcontainers.org/
- Gradle Build Tool: https://gradle.org/
- CQRS Pattern: https://martinfowler.com/bliki/CQRS.html