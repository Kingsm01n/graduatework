package com.vladyslavberezovskyi.dao.repository;

import com.vladyslavberezovskyi.dao.entity.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BalanceRepository extends JpaRepository<BalanceEntity, UUID> {
}
