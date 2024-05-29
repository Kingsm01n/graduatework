package com.vladyslavberezovskyi.mapper;

import com.vladyslavberezovskyi.dao.entity.BalanceEntity;
import com.vladyslavberezovskyi.dao.entity.TransactionEntity;
import com.vladyslavberezovskyi.dto.balance.BalanceResponse;
import com.vladyslavberezovskyi.dto.balance.CreateBalanceRequest;
import com.vladyslavberezovskyi.dto.balance.PatchBalanceRequest;
import com.vladyslavberezovskyi.model.Balance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = {TransactionMapper.class})//TODO map ignores
public interface BalanceMapper {

    BalanceResponse modelToDto(Balance balance);

    default List<BalanceResponse> modelToDto(@Nullable List<Balance> balances) {
        if (balances == null) {
            return null;
        }

        return balances.stream()
                .map(this::modelToDto)
                .toList();
    }

    default Page<BalanceResponse> modelToDto(@Nullable Page<Balance> balances) {
        if (balances == null) {
            return null;
        }

        return balances.map(this::modelToDto);
    }

    Balance dtoToModel(CreateBalanceRequest createBalanceRequest);

    Balance dtoToModel(PatchBalanceRequest patchBalanceRequest);

    Balance entityToModel(BalanceEntity balanceEntity);

    default List<Balance> entityToModel(@Nullable List<BalanceEntity> balanceEntities) {
        if (balanceEntities == null) {
            return null;
        }

        return balanceEntities.stream()
                .map(this::entityToModel)
                .toList();
    }

    default Page<Balance> entityToModel(@Nullable Page<BalanceEntity> balanceEntities) {
        if (balanceEntities == null) {
            return null;
        }

        return balanceEntities.map(this::entityToModel);
    }

    @Mapping(target = "transactions", ignore = true)
    BalanceEntity modelToEntity(Balance balance);

    @Mapping(target = "transactions", ignore = true)
    void update(@MappingTarget BalanceEntity balanceEntity, Balance balance);
}
