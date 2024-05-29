package com.vladyslavberezovskyi.controller;

import com.vladyslavberezovskyi.ITBase;
import com.vladyslavberezovskyi.dao.entity.BalanceEntity;
import com.vladyslavberezovskyi.dao.repository.BalanceRepository;
import com.vladyslavberezovskyi.dto.balance.BalanceResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BalanceControllerGetIT extends ITBase {

    private static final String BALANCE_URL_TEMPLATE = "/api/v1/balances";

    @Autowired
    private BalanceRepository repository;

    @Test
    public void getById_correctRequest_200() throws Exception {
        BalanceEntity balanceEntity = getTestBalanceEntity();
        balanceEntity = repository.saveAndFlush(balanceEntity);

        String jsonResponse = mockMvc.perform(get(BALANCE_URL_TEMPLATE + "/{balanceId}", balanceEntity.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        BalanceResponse response = objectMapper.convertValue(jsonResponse, BalanceResponse.class);

        assertEquals(balanceEntity.getId(), response.getId());
    }

    private BalanceEntity getTestBalanceEntity() {
        return BalanceEntity.builder()
                .build();
    }
}
