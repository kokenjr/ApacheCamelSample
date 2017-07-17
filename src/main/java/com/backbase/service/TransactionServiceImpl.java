package com.backbase.service;

import com.backbase.common.Constants;
import com.backbase.domain.Transaction;
import com.backbase.domain.TransactionAmountResponse;
import com.backbase.domain.TransactionResponse;
import org.apache.http.util.TextUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by korji on 7/13/17.
 */
@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {
    public List<Transaction> getTransactions(String type) {
        RestTemplate restTemplate = new RestTemplate();
        TransactionResponse transactionResponse = restTemplate.getForObject("https:" + Constants.OPEN_BANK_URI, TransactionResponse.class);
        List<Transaction> transactions = transactionResponse.getTransactions();
        if (!TextUtils.isEmpty(type)) {
            transactions = transactions.stream().filter(t -> type.equals(t.getTransactionType())).collect(Collectors.toList());
        }
        return transactions;
    }

    @Override
    public TransactionAmountResponse getTransactionTotalAmount(String type) {
        List<Transaction> transactions = getTransactions(type);
        Double amount = transactions.stream().mapToDouble(Transaction::getTransactionAmount).sum();
        TransactionAmountResponse amountResponse = new TransactionAmountResponse();
        amountResponse.setTotalAmount(amount.floatValue());
        return amountResponse;
    }
}
