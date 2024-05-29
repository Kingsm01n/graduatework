package com.vladyslavberezovskyi.controller;

import com.vladyslavberezovskyi.dto.user.AuthUserRequest;
import com.vladyslavberezovskyi.dto.user.CreateUserRequest;
import com.vladyslavberezovskyi.dto.user.UserResponse;
import com.vladyslavberezovskyi.mapper.UserMapper;
import com.vladyslavberezovskyi.security.TokenResponse;
import com.vladyslavberezovskyi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping(path = "/{id}")
    public UserResponse getUserById(@PathVariable UUID id) {
        return mapper.modelToDto(service.getUserById(id));
    }

    @PostMapping
    private UserResponse createUser(@RequestBody CreateUserRequest createUserRequest) {
        return mapper.modelToDto(service.createUser(mapper.dtoToModel(createUserRequest)));
    }

    @PostMapping("/auth")
    public TokenResponse authenticateUser(@RequestBody AuthUserRequest authUserRequest) {
        return service.auth(mapper.toModel(authUserRequest));
    }
}
