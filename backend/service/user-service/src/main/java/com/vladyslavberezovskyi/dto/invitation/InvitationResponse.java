package com.vladyslavberezovskyi.dto.invitation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationResponse {

    private UUID id;
    private String email;
    private boolean emailConfirmed;
    private String phoneNumber;
    private boolean phoneNumberConfirmed;
    private UUID user;

}
