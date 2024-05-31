package com.vladyslavberezovskyi.controller;

import com.vladyslavberezovskyi.dto.user.AuthUserRequest;
import com.vladyslavberezovskyi.dto.user.CreateUserRequest;
import com.vladyslavberezovskyi.dto.user.UpdateUserRequest;
import com.vladyslavberezovskyi.dto.user.UserResponse;
import com.vladyslavberezovskyi.mapper.UserMapper;
import com.vladyslavberezovskyi.security.TokenResponse;
import com.vladyslavberezovskyi.security.annotations.IsUser;
import com.vladyslavberezovskyi.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @IsUser
    @GetMapping("/user")
    @SecurityRequirement(name = "Authorization")
    public UserResponse getUser() {
        return mapper.modelToDto(service.getUserById());
    }

    @PostMapping("/users")
    private UserResponse createUser(@RequestBody CreateUserRequest createUserRequest) {
        return mapper.modelToDto(service.createUser(mapper.dtoToModel(createUserRequest)));
    }

    @PostMapping("/users/auth")
    public TokenResponse authenticateUser(@RequestBody AuthUserRequest authUserRequest) {
        return service.auth(mapper.dtoToModel(authUserRequest));
    }

    @PatchMapping("/user")
    @SecurityRequirement(name = "Authorization")
    public UserResponse updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        return mapper.modelToDto(service.updateUser(mapper.dtoToModel(updateUserRequest)));
    }
}
