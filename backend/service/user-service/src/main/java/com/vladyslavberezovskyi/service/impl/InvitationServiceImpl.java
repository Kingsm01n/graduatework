package com.vladyslavberezovskyi.service.impl;

import com.vladyslavberezovskyi.dao.entity.InvitationEntity;
import com.vladyslavberezovskyi.dao.entity.UserEntity;
import com.vladyslavberezovskyi.dao.repository.InvitationRepository;
import com.vladyslavberezovskyi.dao.repository.UserRepository;
import com.vladyslavberezovskyi.error.ResourceNotFoundException;
import com.vladyslavberezovskyi.error.UserAlreadyExistsException;
import com.vladyslavberezovskyi.mapper.InvitationMapper;
import com.vladyslavberezovskyi.model.Invitation;
import com.vladyslavberezovskyi.service.InvitationService;
import com.vladyslavberezovskyi.util.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final InvitationMapper mapper;
    private final UserRepository userRepository;
    private final EmailSender emailSender;

    @Override
    public void sendInvitation(UserEntity user) {
        InvitationEntity invitationEntity = new InvitationEntity();
        invitationEntity.setEmail(user.getEmail());

        user.setInvitation(invitationEntity);

        invitationEntity = invitationRepository.saveAndFlush(invitationEntity);
        userRepository.saveAndFlush(user);

        try {
            emailSender.sendInvitation(user.getEmail(), invitationEntity.getId());
        } catch (Exception e) {
            userRepository.delete(user);
            invitationRepository.delete(invitationEntity);
        }
    }

    @Override
    public Invitation getInvitationById(UUID id) {
        InvitationEntity invitationEntity = invitationRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        return mapper.entityToModel(invitationEntity);
    }

    @Override
    public Invitation confirmPhoneNumber(UUID id) {
        InvitationEntity invitationEntity = invitationRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        invitationEntity.setPhoneNumberConfirmed(true);
        invitationEntity = invitationRepository.saveAndFlush(invitationEntity);

        return mapper.entityToModel(invitationEntity);
    }

    @Override
    public Invitation sendPhoneNumberConfirmationRequest(Invitation invitation) {
        if (userRepository.existsByPhoneNumber(invitation.getPhoneNumber())) {
            throw new UserAlreadyExistsException();
        }

        InvitationEntity invitationEntity = mapper.modelToEntity(invitation);
        invitationEntity = invitationRepository.saveAndFlush(invitationEntity);

        //TODO send msg

        return mapper.entityToModel(invitationEntity);
    }

    @Override
    public Invitation confirmInvitation(UUID id) {
        InvitationEntity invitationEntity = invitationRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        invitationEntity.setEmailConfirmed(true);
        invitationEntity = invitationRepository.saveAndFlush(invitationEntity);

        UserEntity user = invitationEntity.getUser();
        user.setEnabled(true);
        userRepository.saveAndFlush(user);

        return mapper.entityToModel(invitationEntity);
    }

}
