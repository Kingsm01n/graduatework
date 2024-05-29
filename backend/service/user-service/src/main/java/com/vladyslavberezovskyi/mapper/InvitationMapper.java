package com.vladyslavberezovskyi.mapper;

import com.vladyslavberezovskyi.dao.entity.InvitationEntity;
import com.vladyslavberezovskyi.dto.invitation.InvitationResponse;
import com.vladyslavberezovskyi.dto.invitation.PhoneNumberConfirmationRequest;
import com.vladyslavberezovskyi.model.Invitation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface InvitationMapper {

    InvitationResponse modelToDto(Invitation invitation);

    @Mapping(target = "user", source = "user.id")
    Invitation entityToModel(InvitationEntity invitationEntity);

    Invitation dtoToModel(PhoneNumberConfirmationRequest phoneNumberConfirmationRequest);

    @Mapping(target = "user", ignore = true)
    InvitationEntity modelToEntity(Invitation invitation);
}
