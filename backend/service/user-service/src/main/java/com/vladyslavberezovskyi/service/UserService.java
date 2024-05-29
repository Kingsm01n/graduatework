package com.vladyslavberezovskyi.service;

import com.vladyslavberezovskyi.dto.user.CreateUserRequest;
import com.vladyslavberezovskyi.model.User;
import com.vladyslavberezovskyi.security.TokenResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);

    User createUser(User user);

    TokenResponse auth(User model);
}
