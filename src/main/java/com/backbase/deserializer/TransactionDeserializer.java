package com.backbase.deserializer;

import com.backbase.domain.Transaction;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * Created by korji on 7/13/17.
 */
public class TransactionDeserializer extends StdDeserializer<Transaction> {

    public TransactionDeserializer() {
        this(null);
    }

    protected TransactionDeserializer(Class<?> vc) {
        super(vc);
    }

    public Transaction deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String id = node.get("id").asText();

        String accountId = null;
        JsonNode jAccountId = node.get("this_account").get("id");
        if (!jAccountId.isNull()){
            accountId = jAccountId.asText();
        }

        String counterpartyAccount = null;
        JsonNode jCoounterpartyAccount = node.get("other_account").get("number");
        if (!jCoounterpartyAccount.isNull()){
            counterpartyAccount = jCoounterpartyAccount.asText();
        }

        String counterpartyName = null;
        JsonNode jcounterpartyName = node.get("other_account").get("holder").get("name");
        if (!jcounterpartyName.isNull()){
            counterpartyName = jcounterpartyName.asText();
        }

        String counterPartyLogoPath = null;
        JsonNode jLogoPath = node.get("other_account").get("metadata").get("image_URL");
        if (!jLogoPath.isNull()){
            counterPartyLogoPath = jLogoPath.asText();
        }

        Double instructedAmount = null;
        JsonNode jInstructedAmount = node.get("details").get("value").get("amount");
        if (!jInstructedAmount.isNull()){
            instructedAmount = jInstructedAmount.asDouble();
        }

        String instructedCurrency = null;
        JsonNode jInstructedCurrency = node.get("details").get("value").get("currency");
        if (!jInstructedCurrency.isNull()){
            instructedCurrency = jInstructedCurrency.asText();
        }

        String transactionType = null;
        JsonNode jTransactionType = node.get("details").get("type");
        if (!jTransactionType.isNull()){
            transactionType = jTransactionType.asText();
        }

        String description = null;
        JsonNode jDescription = node.get("details").get("description");
        if (!jDescription.isNull()) {
            description = jDescription.asText();
        }

        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setAccountId(accountId);
        transaction.setCounterpartyAccount(counterpartyAccount);
        transaction.setCounterpartyName(counterpartyName);
        transaction.setCounterPartyLogoPath(counterPartyLogoPath);
        if (instructedAmount != null) {
            transaction.setInstructedAmount(instructedAmount.floatValue());
        }
        transaction.setInstructedCurrency(instructedCurrency);
        if (instructedAmount != null) {
            transaction.setTransactionAmount(instructedAmount.floatValue());
        }
        transaction.setTransactionCurrency(instructedCurrency);
        transaction.setTransactionType(transactionType);
        transaction.setDescription(description);

        return transaction;
    }
}
