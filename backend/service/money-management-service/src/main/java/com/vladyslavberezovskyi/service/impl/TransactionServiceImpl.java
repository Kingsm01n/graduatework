package com.vladyslavberezovskyi.service.impl;

import com.vladyslavberezovskyi.dao.entity.BalanceEntity;
import com.vladyslavberezovskyi.dao.entity.BudgetEntity;
import com.vladyslavberezovskyi.dao.entity.CategoryEntity;
import com.vladyslavberezovskyi.dao.entity.TransactionEntity;
import com.vladyslavberezovskyi.dao.repository.BalanceRepository;
import com.vladyslavberezovskyi.dao.repository.BudgetRepository;
import com.vladyslavberezovskyi.dao.repository.CategoryRepository;
import com.vladyslavberezovskyi.dao.repository.TransactionRepository;
import com.vladyslavberezovskyi.error.ResourceNotFoundException;
import com.vladyslavberezovskyi.mapper.TransactionMapper;
import com.vladyslavberezovskyi.model.Transaction;
import com.vladyslavberezovskyi.security.UserDetails;
import com.vladyslavberezovskyi.service.TransactionService;
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
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final TransactionMapper mapper;
    private final CategoryRepository categoryRepository;
    private final BudgetRepository budgetRepository;
    private final BalanceRepository balanceRepository;
    private final EmailSender emailSender;

    @Override
    public Transaction getTransactionById(UUID transactionId) {
        return mapper.entityToModel(repository.findById(transactionId)
                .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public List<Transaction> getAllTransactions(UUID balanceId) {
        if (balanceId != null) {
            return mapper.entityToModel(repository.findAllByBalanceId(balanceId));
        } else {
            return mapper.entityToModel(repository.findAll());
        }
    }

    @Override
    public Page<Transaction> getAllTransactions(Pageable pageable) {
        return mapper.entityToModel(repository.findAll(pageable));
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        BalanceEntity balanceEntity = balanceRepository.findById(transaction.getBalance())
                .orElseThrow(ResourceNotFoundException::new);

        if ((balanceEntity.getCurrentValue() + transaction.getValue()) >= 0) {
            balanceEntity.setCurrentValue(balanceEntity.getCurrentValue() + transaction.getValue());
            balanceRepository.saveAndFlush(balanceEntity);
        } else {
            throw new RuntimeException();
        }

        TransactionEntity transactionEntity = mapper.modelToEntity(transaction);

        transactionEntity.setBalance(balanceEntity);
        CategoryEntity categoryEntity = categoryRepository.findById(transaction.getCategory())
                .orElseThrow(ResourceNotFoundException::new);
        transactionEntity.setCategory(categoryEntity);

        List<BudgetEntity> budgetEntities = budgetRepository.findAllByCategory(categoryEntity);
        budgetEntities.forEach(budgetEntity ->
                budgetEntity.setCurrentValue(budgetEntity.getCurrentValue() - transaction.getValue()));
        budgetRepository.saveAllAndFlush(budgetEntities);

        budgetEntities.forEach(budgetEntity -> {
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
        });


        transactionEntity = repository.saveAndFlush(transactionEntity);

        return mapper.entityToModel(transactionEntity);
    }

    @Override
    public Transaction updateTransaction(UUID transactionId, Transaction transaction) {
        TransactionEntity transactionEntity = repository.findById(transactionId)
                .orElseThrow(ResourceNotFoundException::new);
        mapper.update(transactionEntity, transaction);
        transactionEntity = repository.saveAndFlush(transactionEntity);

        return mapper.entityToModel(transactionEntity);
    }
}
