package com.vladyslavberezovskyi.dao.entity;

import com.vladyslavberezovskyi.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Builder
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "invitation")
@AllArgsConstructor
public class InvitationEntity extends BaseEntity {

    private String email;
    private boolean emailConfirmed;
    private String phoneNumber;
    private boolean phoneNumberConfirmed;

    @OneToOne(mappedBy = "invitation")
    private UserEntity user;

}
