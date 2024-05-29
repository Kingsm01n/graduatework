package com.vladyslavberezovskyi.service.impl;

import com.vladyslavberezovskyi.dao.entity.BalanceEntity;
import com.vladyslavberezovskyi.dao.repository.BalanceRepository;
import com.vladyslavberezovskyi.error.ResourceNotFoundException;
import com.vladyslavberezovskyi.mapper.BalanceMapper;
import com.vladyslavberezovskyi.model.Balance;
import com.vladyslavberezovskyi.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor //TODO add userId to queries
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository repository;
    private final BalanceMapper mapper;

    @Override
    public Balance getBalanceById(UUID balanceId) {
        return mapper.entityToModel(repository.findById(balanceId)
                .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public List<Balance> getAllBalances() {
        return mapper.entityToModel(repository.findAll());
    }

    @Override
    public Page<Balance> getAllBalances(Pageable pageable) {
        return mapper.entityToModel(repository.findAll(pageable));
    }

    @Override
    public Balance createBalance(Balance balance) {
        BalanceEntity balanceEntity = mapper.modelToEntity(balance);
        balanceEntity = repository.saveAndFlush(balanceEntity);

        return mapper.entityToModel(balanceEntity);
    }

    @Override
    public Balance updateBalance(UUID balanceId, Balance balance) {
        BalanceEntity balanceEntity = repository.findById(balanceId)
                .orElseThrow(ResourceNotFoundException::new);
        mapper.update(balanceEntity, balance);
        repository.saveAndFlush(balanceEntity);

        return mapper.entityToModel(balanceEntity);
    }
}
