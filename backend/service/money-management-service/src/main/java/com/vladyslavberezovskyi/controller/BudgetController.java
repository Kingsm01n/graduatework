package com.vladyslavberezovskyi.controller;

import com.vladyslavberezovskyi.dto.budget.BudgetResponse;
import com.vladyslavberezovskyi.dto.budget.CreateBudgetRequest;
import com.vladyslavberezovskyi.dto.budget.PatchBudgetRequest;
import com.vladyslavberezovskyi.mapper.BudgetMapper;
import com.vladyslavberezovskyi.mapper.PageMapper;
import com.vladyslavberezovskyi.security.annotations.IsUser;
import com.vladyslavberezovskyi.service.BudgetService;
import com.vladyslavberezovskyi.util.PageRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/budgets")
@SecurityRequirement(name = "Authorization")
public class BudgetController {

    private final BudgetService service;
    private final BudgetMapper mapper;
    private final PageMapper pageMapper;

    @IsUser
    @GetMapping("/{budgetId}")
    public BudgetResponse getBudgetById(@PathVariable UUID budgetId) {
        return mapper.modelToDto(service.getBudgetById(budgetId));
    }

    @IsUser
    @GetMapping
    public List<BudgetResponse> getAllBudgets() {
        return mapper.modelToDto(service.getAllBudgets());
    }

    @IsUser
    @GetMapping("/paged")
    public Page<BudgetResponse> getAllBudgets(PageRequest pageRequest) {
        return mapper.modelToDto(service.getAllBudgets(pageMapper.pageRequestToPageable(pageRequest)));
    }

    @IsUser
    @PostMapping
    public BudgetResponse createBudget(@RequestBody CreateBudgetRequest createBudgetRequest) {
        return mapper.modelToDto(service.createBudget(mapper.dtoToModel(createBudgetRequest)));
    }

    @IsUser
    @PatchMapping("/{budgetId}")
    public BudgetResponse updateBudget(@PathVariable UUID budgetId,
                                       @RequestBody PatchBudgetRequest patchBudgetRequest) {
        return mapper.modelToDto(service.updateBudget(budgetId, mapper.dtoToModel(patchBudgetRequest)));
    }

    @IsUser
    @DeleteMapping("/{budgetId}")
    public void deleteBudget(@PathVariable UUID budgetId) {
        service.deleteBudget(budgetId);
    }
}
