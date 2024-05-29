package com.vladyslavberezovskyi.dao.entity;

import com.vladyslavberezovskyi.security.enums.Role;
import com.vladyslavberezovskyi.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "user")
@Table(name = "\"user\"")
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    private String email;
    private String nickname;
    private String password;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
    private boolean enabled;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "invitation_id")
    private InvitationEntity invitation;

}
