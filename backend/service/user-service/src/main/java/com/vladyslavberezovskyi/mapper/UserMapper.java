package com.vladyslavberezovskyi.mapper;

import com.vladyslavberezovskyi.dto.user.AuthUserRequest;
import com.vladyslavberezovskyi.security.UserDetails;
import com.vladyslavberezovskyi.dto.user.CreateUserRequest;
import com.vladyslavberezovskyi.dto.user.UserResponse;
import com.vladyslavberezovskyi.dao.entity.UserEntity;
import com.vladyslavberezovskyi.model.User;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    UserResponse modelToDto(User user);

    User entityToModel(UserEntity userEntity);

    User dtoToModel(CreateUserRequest createUserRequest);

    UserEntity modelToEntity(User user);

    User toModel(AuthUserRequest authUserRequest);
}
