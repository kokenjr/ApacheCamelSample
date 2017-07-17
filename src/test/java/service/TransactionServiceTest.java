package service;

import com.backbase.domain.Transaction;
import com.backbase.domain.TransactionAmountResponse;
import com.backbase.service.TransactionService;
import com.backbase.spring.AppConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by korji on 7/14/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;
    private String transactionType = "sandbox-payment";
    private String fakeTransactionType = "fake-transaction-type";

    @Test
    public void getTransactionsTest() {

        List<Transaction> transactions = transactionService.getTransactions(transactionType);
        Assert.assertNotNull(transactions);
        Assert.assertTrue(!transactions.isEmpty());
        Transaction transaction = transactions.get(0);
        Assert.assertEquals(transaction.getTransactionType(), transactionType);
    }

    @Test
    public void getTransactionsNegativeTest() {
        List<Transaction> transactions = transactionService.getTransactions(fakeTransactionType);
        Assert.assertNotNull(transactions);
        Assert.assertTrue(transactions.isEmpty());
    }

    @Test
    public void getTransactionsTotalAmountTest() {
        TransactionAmountResponse transactionTotalAmount = transactionService.getTransactionTotalAmount(transactionType);
        Assert.assertNotNull(transactionTotalAmount);
        Assert.assertNotNull(transactionTotalAmount.getTotalAmount());
    }

    @Test
    public void getTransactionsTotalAmountNegativeTest() {
        TransactionAmountResponse transactionTotalAmount = transactionService.getTransactionTotalAmount(fakeTransactionType);
        Assert.assertNotNull(transactionTotalAmount);
        Assert.assertNotNull(transactionTotalAmount.getTotalAmount());
        Assert.assertEquals(transactionTotalAmount.getTotalAmount().longValue(), 0L);
    }
}
