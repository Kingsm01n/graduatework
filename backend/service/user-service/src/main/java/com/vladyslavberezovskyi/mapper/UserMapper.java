package com.vladyslavberezovskyi.mapper;

import com.vladyslavberezovskyi.dto.user.AuthUserRequest;
import com.vladyslavberezovskyi.dto.user.CreateUserRequest;
import com.vladyslavberezovskyi.dto.user.UpdateUserRequest;
import com.vladyslavberezovskyi.dto.user.UserResponse;
import com.vladyslavberezovskyi.dao.entity.UserEntity;
import com.vladyslavberezovskyi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    UserResponse modelToDto(User user);

    @Mapping(target = "name", source = "nickname")
    User entityToModel(UserEntity userEntity);

    User dtoToModel(CreateUserRequest createUserRequest);

    User dtoToModel(UpdateUserRequest updateUserRequest);

    UserEntity modelToEntity(User user);

    User dtoToModel(AuthUserRequest authUserRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nickname", source = "name")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    void update(@MappingTarget UserEntity userEntity, User user);
}
