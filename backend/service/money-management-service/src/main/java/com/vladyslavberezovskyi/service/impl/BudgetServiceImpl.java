package com.vladyslavberezovskyi.service.impl;

import com.vladyslavberezovskyi.dao.entity.BudgetEntity;
import com.vladyslavberezovskyi.dao.entity.CategoryEntity;
import com.vladyslavberezovskyi.dao.entity.TransactionEntity;
import com.vladyslavberezovskyi.dao.repository.BudgetRepository;
import com.vladyslavberezovskyi.dao.repository.CategoryRepository;
import com.vladyslavberezovskyi.dao.repository.TransactionRepository;
import com.vladyslavberezovskyi.error.ResourceNotFoundException;
import com.vladyslavberezovskyi.mapper.BudgetMapper;
import com.vladyslavberezovskyi.model.Budget;
import com.vladyslavberezovskyi.security.UserDetails;
import com.vladyslavberezovskyi.service.BudgetService;
import com.vladyslavberezovskyi.util.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor//TODO add userId to queries
public class BudgetServiceImpl implements BudgetService {

    private final BudgetMapper mapper;
    private final BudgetRepository repository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final EmailSender emailSender;

    @Override
    public Budget getBudgetById(UUID budgetId) {
        return mapper.entityToModel(repository.findById(budgetId)
                .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public List<Budget> getAllBudgets() {
        return mapper.entityToModel(repository.findAll());
    }

    @Override
    public Page<Budget> getAllBudgets(Pageable pageable) {
        return mapper.entityToModel(repository.findAll(pageable));
    }

    @Override
    public Budget createBudget(Budget budget) {
        BudgetEntity budgetEntity = mapper.modelToEntity(budget);

        CategoryEntity categoryEntity = categoryRepository.findById(budget.getCategory())
                        .orElseThrow(ResourceNotFoundException::new);
        budgetEntity.setCategory(categoryEntity);

        List<TransactionEntity> transactionEntities = transactionRepository.findAllByCategory(categoryEntity);

        for (TransactionEntity transactionEntity : transactionEntities) {
            budgetEntity.setCurrentValue(budgetEntity.getCurrentValue() - transactionEntity.getValue());
        }

        budgetEntity = repository.saveAndFlush(budgetEntity);

        return mapper.entityToModel(budgetEntity);
    }

    @Override
    public Budget updateBudget(UUID budgetId, Budget budget) {
        BudgetEntity budgetEntity = repository.findById(budgetId)
                .orElseThrow(ResourceNotFoundException::new);
        mapper.update(budgetEntity, budget);
        repository.saveAndFlush(budgetEntity);

        if (budgetEntity.getCurrentValue() >= budgetEntity.getNeededValue()) {
            String email = ((UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal())
                    .getUsername();
            try {
                emailSender.sendWarning(email,
                        budgetEntity.getName(),
                        budgetEntity.getCurrentValue() + "/" + budgetEntity.getNeededValue());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return mapper.entityToModel(budgetEntity);
    }

    @Override
    public void deleteBudget(UUID budgetId) {
        BudgetEntity budgetEntity = repository.findById(budgetId)
                        .orElseThrow(ResourceNotFoundException::new);

        repository.delete(budgetEntity);
    }
}
