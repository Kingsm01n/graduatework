package com.vladyslavberezovskyi.service;

import com.vladyslavberezovskyi.dao.entity.UserEntity;
import com.vladyslavberezovskyi.dto.invitation.PhoneNumberConfirmationRequest;
import com.vladyslavberezovskyi.model.Invitation;

import java.util.UUID;

public interface InvitationService {

    void sendInvitation(UserEntity savedUser);

    Invitation getInvitationById(UUID id);

    Invitation confirmPhoneNumber(UUID id);

    Invitation sendPhoneNumberConfirmationRequest(Invitation invitation);

    Invitation confirmInvitation(UUID id);
}
