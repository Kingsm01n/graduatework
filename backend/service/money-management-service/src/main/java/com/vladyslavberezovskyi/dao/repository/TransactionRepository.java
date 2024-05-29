package com.vladyslavberezovskyi.dao.repository;

import com.vladyslavberezovskyi.dao.entity.CategoryEntity;
import com.vladyslavberezovskyi.dao.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findAllByCategory(CategoryEntity categoryEntity);

    List<TransactionEntity> findAllByBalanceId(UUID balanceId);
}
