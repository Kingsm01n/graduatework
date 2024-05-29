package com.vladyslavberezovskyi.controller;

import com.vladyslavberezovskyi.dto.invitation.InvitationResponse;
import com.vladyslavberezovskyi.dto.invitation.PhoneNumberConfirmationRequest;
import com.vladyslavberezovskyi.mapper.InvitationMapper;
import com.vladyslavberezovskyi.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/invitations")
public class InvitationController {

    private final InvitationMapper invitationMapper;
    private final InvitationService invitationService;

    @PostMapping(path = "/confirmPhoneNumber/{id}")
    public InvitationResponse confirmPhoneNumber(@PathVariable UUID id) {
        return invitationMapper.modelToDto(invitationService.confirmPhoneNumber(id));
    }

    @PostMapping(path = "/sendPhoneNumberConfirmationRequest")
    public InvitationResponse sendPhoneNumberConfirmationRequest(@RequestBody PhoneNumberConfirmationRequest phoneNumberConfirmationRequest) {
        return invitationMapper.modelToDto(invitationService.sendPhoneNumberConfirmationRequest(invitationMapper.dtoToModel(phoneNumberConfirmationRequest)));
    }

    @GetMapping(path = "/{id}")
    public InvitationResponse getInvitationById(@PathVariable UUID id) {
        return invitationMapper.modelToDto(invitationService.getInvitationById(id));
    }

    @GetMapping(path = "/confirmInvitation/{id}")
    public InvitationResponse confirmInvitation(@PathVariable UUID id) {
        return invitationMapper.modelToDto(invitationService.confirmInvitation(id));
    }
}
