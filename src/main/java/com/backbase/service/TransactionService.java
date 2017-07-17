package com.backbase.service;

import com.backbase.domain.Transaction;
import com.backbase.domain.TransactionAmountResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by korji on 7/13/17.
 */
public interface TransactionService {
    List<Transaction> getTransactions(String type);
    TransactionAmountResponse getTransactionTotalAmount(String type);
}
