package com.vladyslavberezovskyi.controller;

import com.vladyslavberezovskyi.dto.balance.CreateBalanceRequest;
import com.vladyslavberezovskyi.dto.balance.PatchBalanceRequest;
import com.vladyslavberezovskyi.mapper.PageMapper;
import com.vladyslavberezovskyi.security.annotations.IsUser;
import com.vladyslavberezovskyi.service.BalanceService;
import com.vladyslavberezovskyi.dto.balance.BalanceResponse;
import com.vladyslavberezovskyi.mapper.BalanceMapper;
import com.vladyslavberezovskyi.util.PageRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/balances")
@SecurityRequirement(name = "Authorization")
public class BalanceController {

    private final BalanceService service;
    private final BalanceMapper mapper;
    private final PageMapper pageMapper;

    @GetMapping(path = "/{balanceId}")
    public BalanceResponse getBalanceById(@PathVariable UUID balanceId) {
        return mapper.modelToDto(service.getBalanceById(balanceId));
    }

    @GetMapping
    public List<BalanceResponse> getAllBalances() {
        return mapper.modelToDto(service.getAllBalances());
    }

    @GetMapping("/paged")
    public Page<BalanceResponse> getAllBalances(PageRequest pageRequest) {
        return mapper.modelToDto(service.getAllBalances(pageMapper.pageRequestToPageable(pageRequest)));
    }

    @PostMapping
    public BalanceResponse createBalance(CreateBalanceRequest createBalanceRequest) {
        return mapper.modelToDto(service.createBalance(mapper.dtoToModel(createBalanceRequest)));
    }

    @PatchMapping(path = "/{balanceId}")
    public BalanceResponse updateBalance(@PathVariable UUID balanceId,
                                         @RequestBody PatchBalanceRequest patchBalanceRequest) {
        return mapper.modelToDto(service.updateBalance(balanceId, mapper.dtoToModel(patchBalanceRequest)));
    }
}
