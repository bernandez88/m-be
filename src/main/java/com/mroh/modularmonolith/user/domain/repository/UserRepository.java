package com.mroh.modularmonolith.user.domain.repository;

import com.mroh.modularmonolith.user.domain.model.User;
import java.util.List;

public interface UserRepository {
    User save(User user);
    List<User> findAll();
}
