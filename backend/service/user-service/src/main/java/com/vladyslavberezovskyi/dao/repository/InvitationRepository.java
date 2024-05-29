package com.vladyslavberezovskyi.dao.repository;

import com.vladyslavberezovskyi.dao.entity.InvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvitationRepository extends JpaRepository<InvitationEntity, UUID> {
    InvitationEntity findByEmail(String phoneNumber);
}
