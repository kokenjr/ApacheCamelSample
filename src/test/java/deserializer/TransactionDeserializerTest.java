package deserializer;

import com.backbase.domain.Transaction;
import com.backbase.domain.TransactionAmountResponse;
import com.backbase.domain.TransactionResponse;
import com.backbase.spring.AppConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by korji on 7/14/17.
 */

public class TransactionDeserializerTest {

    @Test
    public void deserializeOpenBankJson() throws IOException {
        String openBankTransactionsJson = "{\n" +
                "  \"transactions\": [\n" +
                "    {\n" +
                "      \"id\": \"dcb8138c-eb88-404a-981d-d4edff1086a6\",\n" +
                "      \"this_account\": {\n" +
                "        \"id\": \"savings-kids-john\",\n" +
                "        \"holders\": [\n" +
                "          {\n" +
                "            \"name\": \"Savings - Kids John\",\n" +
                "            \"is_alias\": false\n" +
                "          }\n" +
                "        ],\n" +
                "        \"number\": \"832425-00304050\",\n" +
                "        \"kind\": \"savings\",\n" +
                "        \"IBAN\": null,\n" +
                "        \"swift_bic\": null,\n" +
                "        \"bank\": {\n" +
                "          \"national_identifier\": \"rbs\",\n" +
                "          \"name\": \"The Royal Bank of Scotland\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"other_account\": {\n" +
                "        \"id\": \"c83f9a12-171e-4602-9a92-ae895c41b16b\",\n" +
                "        \"holder\": {\n" +
                "          \"name\": \"ALIAS_CBCDE5\",\n" +
                "          \"is_alias\": true\n" +
                "        },\n" +
                "        \"number\": \"13677980653\",\n" +
                "        \"kind\": \"CURRENT PLUS\",\n" +
                "        \"IBAN\": \"BA12 1234 5123 4513 6779 8065 377\",\n" +
                "        \"swift_bic\": null,\n" +
                "        \"bank\": {\n" +
                "          \"national_identifier\": null,\n" +
                "          \"name\": \"The Bank of X\"\n" +
                "        },\n" +
                "        \"metadata\": {\n" +
                "          \"public_alias\": null,\n" +
                "          \"private_alias\": null,\n" +
                "          \"more_info\": null,\n" +
                "          \"URL\": null,\n" +
                "          \"image_URL\": null,\n" +
                "          \"open_corporates_URL\": null,\n" +
                "          \"corporate_location\": null,\n" +
                "          \"physical_location\": null\n" +
                "        }\n" +
                "      },\n" +
                "      \"details\": {\n" +
                "        \"type\": \"sandbox-payment\",\n" +
                "        \"description\": \"Description abc\",\n" +
                "        \"posted\": \"2016-10-09T20:01:53Z\",\n" +
                "        \"completed\": \"2016-10-09T20:01:53Z\",\n" +
                "        \"new_balance\": {\n" +
                "          \"currency\": \"GBP\",\n" +
                "          \"amount\": null\n" +
                "        },\n" +
                "        \"value\": {\n" +
                "          \"currency\": \"GBP\",\n" +
                "          \"amount\": \"10.00\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"metadata\": {\n" +
                "        \"narrative\": null,\n" +
                "        \"comments\": [],\n" +
                "        \"tags\": [],\n" +
                "        \"images\": [],\n" +
                "        \"where\": null\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        ObjectMapper objectMapper = new ObjectMapper();
        TransactionResponse transactionResponse = objectMapper.readValue(openBankTransactionsJson, TransactionResponse.class);
        Assert.assertNotNull(transactionResponse);
        Assert.assertNotNull(transactionResponse.getTransactions());
        Assert.assertFalse(transactionResponse.getTransactions().isEmpty());
        Transaction transaction= transactionResponse.getTransactions().get(0);
        Assert.assertEquals(transaction.getId(), "dcb8138c-eb88-404a-981d-d4edff1086a6");
        Assert.assertEquals(transaction.getAccountId(), "savings-kids-john");
    }
}
