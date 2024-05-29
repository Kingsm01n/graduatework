package com.vladyslavberezovskyi.service;

import com.vladyslavberezovskyi.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    Transaction getTransactionById(UUID transactionId);

    List<Transaction> getAllTransactions(UUID balanceId);

    Page<Transaction> getAllTransactions(Pageable pageable);

    Transaction createTransaction(Transaction transaction);

    Transaction updateTransaction(UUID transactionId, Transaction transaction);

}
