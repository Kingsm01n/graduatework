package com.vladyslavberezovskyi.service.impl;

import com.vladyslavberezovskyi.dao.entity.CreditEntity;
import com.vladyslavberezovskyi.dao.repository.CreditRepository;
import com.vladyslavberezovskyi.error.ResourceNotFoundException;
import com.vladyslavberezovskyi.mapper.CreditMapper;
import com.vladyslavberezovskyi.model.Credit;
import com.vladyslavberezovskyi.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor//TODO add userId to queries
public class CreditServiceImpl implements CreditService {

    private final CreditMapper mapper;
    private final CreditRepository repository;

    @Override
    public Credit getCreditById(UUID creditId) {
        return mapper.entityToModel(repository.findById(creditId)
                .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public Page<Credit> getAllCredits(Pageable pageable) {
        return mapper.entityToModel(repository.findAll(pageable));
    }

    @Override
    public Credit createCredit(Credit credit) {
        CreditEntity creditEntity = mapper.modelToEntity(credit);
        creditEntity = repository.saveAndFlush(creditEntity);

        return mapper.entityToModel(creditEntity);
    }

    @Override
    public Credit updateCredit(UUID creditId, Credit credit) {
        CreditEntity creditEntity = repository.findById(creditId)
                .orElseThrow(ResourceNotFoundException::new);
        mapper.update(creditEntity, credit);
        creditEntity = repository.saveAndFlush(creditEntity);

        return mapper.entityToModel(creditEntity);
    }
}
