package com.vladyslavberezovskyi.controller;

import com.vladyslavberezovskyi.dto.transaction.CreateTransactionRequest;
import com.vladyslavberezovskyi.dto.transaction.PatchTransactionRequest;
import com.vladyslavberezovskyi.dto.transaction.TransactionResponse;
import com.vladyslavberezovskyi.mapper.PageMapper;
import com.vladyslavberezovskyi.mapper.TransactionMapper;
import com.vladyslavberezovskyi.security.annotations.IsUser;
import com.vladyslavberezovskyi.service.TransactionService;
import com.vladyslavberezovskyi.util.PageRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
@SecurityRequirement(name = "Authorization")
public class TransactionController {

    private final TransactionService service;
    private final TransactionMapper mapper;
    private final PageMapper pageMapper;

    @IsUser
    @GetMapping("/{transactionId}")
    public TransactionResponse getTransactionById(@PathVariable UUID transactionId) {
        return mapper.modelToDto(service.getTransactionById(transactionId));
    }

    @IsUser
    @GetMapping
    public List<TransactionResponse> getAllTransactions(@RequestParam @Nullable UUID balanceId) {
        return mapper.modelToDto(service.getAllTransactions(balanceId));
    }

    @IsUser
    @GetMapping("/paged")
    public Page<TransactionResponse> getAllTransactions(PageRequest pageRequest) {
        return mapper.modelToDto(service.getAllTransactions(pageMapper.pageRequestToPageable(pageRequest)));
    }

    @IsUser
    @PostMapping
    public TransactionResponse createTransaction(@RequestBody CreateTransactionRequest createTransactionRequest) {
        return mapper.modelToDto(service.createTransaction(mapper.dtoToModel(createTransactionRequest)));
    }

    @IsUser
    @PatchMapping("/{transactionId}")
    public TransactionResponse updateTransaction(@PathVariable UUID transactionId,
                                                 @RequestBody PatchTransactionRequest patchTransactionRequest) {
        return mapper.modelToDto(service.updateTransaction(transactionId, mapper.dtoToModel(patchTransactionRequest)));
    }
}
