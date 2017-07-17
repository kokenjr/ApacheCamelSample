package com.backbase.camel.processor;

import com.backbase.domain.TransactionResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by korji on 7/14/17.
 */
public class TransactionsProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        TransactionResponse transactionResponse = exchange.getIn().getBody(TransactionResponse.class);
        exchange.getOut().setBody(transactionResponse.getTransactions());
    }
}
