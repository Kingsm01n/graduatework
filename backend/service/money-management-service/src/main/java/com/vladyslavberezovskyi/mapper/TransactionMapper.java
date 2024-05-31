package com.vladyslavberezovskyi.mapper;

import com.vladyslavberezovskyi.dao.entity.TransactionEntity;
import com.vladyslavberezovskyi.dto.transaction.CreateTransactionRequest;
import com.vladyslavberezovskyi.dto.transaction.PatchTransactionRequest;
import com.vladyslavberezovskyi.dto.transaction.TransactionResponse;
import com.vladyslavberezovskyi.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = {BudgetMapper.class})//TODO map ignores
public interface TransactionMapper {
    TransactionResponse modelToDto(Transaction transaction);

    default List<TransactionResponse> modelToDto(@Nullable List<Transaction> transactions) {
        if (transactions == null) {
            return null;
        }

        return transactions.stream()
                .map(this::modelToDto)
                .toList();
    }

    default Page<TransactionResponse> modelToDto(@Nullable Page<Transaction> transactions) {
        if (transactions == null) {
            return null;
        }

        return transactions.map(this::modelToDto);
    }

    Transaction dtoToModel(CreateTransactionRequest createTransactionRequest);

    Transaction dtoToModel(PatchTransactionRequest patchTransactionRequest);

    @Mapping(target = "balance", source = "balance.id")
    @Mapping(target = "category", source = "category.id")
    Transaction entityToModel(TransactionEntity transactionEntity);

    default List<Transaction> entityToModel(@Nullable List<TransactionEntity> transactionEntities) {
        if (transactionEntities == null) {
            return null;
        }

        return transactionEntities.stream()
                .map(this::entityToModel)
                .toList();
    }

    default Page<Transaction> entityToModel(@Nullable Page<TransactionEntity> transactionEntities) {
        if (transactionEntities == null) {
            return null;
        }

        return transactionEntities.map(this::entityToModel);
    }

    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "category", ignore = true)
    TransactionEntity modelToEntity(Transaction transaction);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "category", ignore = true)
    void update(@MappingTarget TransactionEntity transactionEntity, Transaction transaction);

    default List<UUID> getIds(@Nullable List<TransactionEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(TransactionEntity::getId)
                .toList();
    }
}
