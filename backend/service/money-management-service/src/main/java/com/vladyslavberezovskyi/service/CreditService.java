package com.vladyslavberezovskyi.service;

import com.vladyslavberezovskyi.model.Credit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CreditService {
    Credit getCreditById(UUID creditId);

    Page<Credit> getAllCredits(Pageable pageable);

    Credit createCredit(Credit credit);

    Credit updateCredit(UUID creditId, Credit credit);
}
