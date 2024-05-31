package com.vladyslavberezovskyi.service;

import com.vladyslavberezovskyi.model.Budget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BudgetService {

    Budget getBudgetById(UUID budgetId);

    List<Budget> getAllBudgets();

    Page<Budget> getAllBudgets(Pageable pageable);

    Budget createBudget(Budget budget);

    Budget updateBudget(UUID budgetId, Budget budget);

    void deleteBudget(UUID budgetId);
}
