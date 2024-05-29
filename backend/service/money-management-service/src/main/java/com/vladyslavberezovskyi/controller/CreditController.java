package com.vladyslavberezovskyi.controller;

import com.vladyslavberezovskyi.dto.credit.CreateCreditRequest;
import com.vladyslavberezovskyi.dto.credit.CreditResponse;
import com.vladyslavberezovskyi.dto.credit.PatchCreditRequest;
import com.vladyslavberezovskyi.mapper.CreditMapper;
import com.vladyslavberezovskyi.mapper.PageMapper;
import com.vladyslavberezovskyi.security.annotations.IsUser;
import com.vladyslavberezovskyi.service.CreditService;
import com.vladyslavberezovskyi.util.PageRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/credits")
@SecurityRequirement(name = "Authorization")
public class CreditController {

    private final CreditService service;
    private final CreditMapper mapper;
    private final PageMapper pageMapper;

    @IsUser
    @GetMapping("/{creditId}")
    public CreditResponse getCreditById(@PathVariable UUID creditId) {
        return mapper.modelToDto(service.getCreditById(creditId));
    }

    @IsUser
    @GetMapping
    public Page<CreditResponse> getAllCredits(PageRequest pageRequest) {
        return mapper.modelToDto(service.getAllCredits(pageMapper.pageRequestToPageable(pageRequest)));
    }

    @IsUser
    @PostMapping
    public CreditResponse createCredit(@RequestBody CreateCreditRequest createCreditRequest) {
        return mapper.modelToDto(service.createCredit(mapper.dtoToModel(createCreditRequest)));
    }

    @IsUser
    @PatchMapping("/{creditId}")
    public CreditResponse updateCredit(@PathVariable UUID creditId,
                                       @RequestBody PatchCreditRequest patchCreditRequest) {
        return mapper.modelToDto(service.updateCredit(creditId, mapper.dtoToModel(patchCreditRequest)));
    }
}
