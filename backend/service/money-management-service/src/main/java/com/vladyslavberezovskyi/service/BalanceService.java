package com.vladyslavberezovskyi.service;

import com.vladyslavberezovskyi.model.Balance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BalanceService {

    Balance getBalanceById(UUID balanceId);

    List<Balance> getAllBalances();

    Page<Balance> getAllBalances(Pageable pageable);

    Balance createBalance(Balance balance);

    Balance updateBalance(UUID balanceId, Balance balance);

    void deleteBalance(UUID balanceId);
}
