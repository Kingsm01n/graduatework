package com.vladyslavberezovskyi.service;

import com.vladyslavberezovskyi.model.User;
import com.vladyslavberezovskyi.security.TokenResponse;

import java.util.UUID;

public interface UserService {
    User getUserById();

    User createUser(User user);

    TokenResponse auth(User model);

    User updateUser(User user);
}
