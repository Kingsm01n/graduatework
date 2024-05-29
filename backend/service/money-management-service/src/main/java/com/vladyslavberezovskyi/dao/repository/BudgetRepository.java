package com.vladyslavberezovskyi.dao.repository;

import com.vladyslavberezovskyi.dao.entity.BudgetEntity;
import com.vladyslavberezovskyi.dao.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity, UUID> {

    List<BudgetEntity> findAllByCategory(CategoryEntity categoryEntity);

}
