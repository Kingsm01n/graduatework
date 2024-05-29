package com.vladyslavberezovskyi.mapper;

import com.vladyslavberezovskyi.dao.entity.BudgetEntity;
import com.vladyslavberezovskyi.dto.budget.BudgetResponse;
import com.vladyslavberezovskyi.dto.budget.CreateBudgetRequest;
import com.vladyslavberezovskyi.dto.budget.PatchBudgetRequest;
import com.vladyslavberezovskyi.model.Budget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.UUID;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = {TransactionMapper.class})//TODO map ignores
public interface BudgetMapper {
    BudgetResponse modelToDto(Budget budget);

    default List<BudgetResponse> modelToDto(@Nullable List<Budget> budgets) {
        if (budgets == null) {
            return null;
        }

        return budgets.stream()
                .map(this::modelToDto)
                .toList();
    }

    default Page<BudgetResponse> modelToDto(@Nullable Page<Budget> budgets) {
        if (budgets == null) {
            return null;
        }

        return budgets.map(this::modelToDto);
    }

    Budget dtoToModel(CreateBudgetRequest createBudgetRequest);

    Budget dtoToModel(PatchBudgetRequest patchBudgetRequest);

    @Mapping(target = "category", source = "category.id")
    Budget entityToModel(BudgetEntity budgetEntity);

    default List<Budget> entityToModel(@Nullable List<BudgetEntity> budgetEntities){
        if (budgetEntities == null) {
            return null;
        }

        return budgetEntities.stream()
                .map(this::entityToModel)
                .toList();
    }

    default Page<Budget> entityToModel(@Nullable Page<BudgetEntity> budgetEntities){
        if (budgetEntities == null) {
            return null;
        }

        return budgetEntities.map(this::entityToModel);
    }

    @Mapping(target = "category", ignore = true)
    BudgetEntity modelToEntity(Budget budget);

    @Mapping(target = "category", ignore = true)
    void update(@MappingTarget BudgetEntity budgetEntity, Budget budget);

    default List<UUID> getIds(@Nullable List<BudgetEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(BudgetEntity::getId)
                .toList();
    }
}
