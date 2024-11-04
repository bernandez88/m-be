package com.mroh.modularmonolith.user.application.query;

import com.mroh.modularmonolith.user.domain.model.User;
import com.mroh.modularmonolith.user.domain.repository.UserRepository;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class GetAllUsersQueryHandler {

    private final UserRepository userRepository;

    public GetAllUsersQueryHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }
}
