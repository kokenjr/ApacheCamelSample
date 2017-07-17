package integration;

import com.backbase.common.Constants;
import com.backbase.domain.TransactionAmountResponse;
import com.backbase.spring.AppConfig;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by korji on 7/14/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
@DirtiesContext
public class TransactionResourceTest {

    private String baseUrl = "http://localhost:8081/api/transactions";

    private RestTemplate getBasicAuthRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(Constants.API_CLIENT, Constants.API_SECRET));
        return restTemplate;
    }

    @Test
    public void getTransactionsTest() throws InterruptedException {
        RestTemplate restTemplate = getBasicAuthRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl, String.class);
        String responseText = response.getBody();
        JSONArray transactions = new JSONArray(responseText);
        Assert.assertNotNull(transactions);
        Assert.assertTrue(transactions.length() > 0);
    }

    @Test
    public void getTransactionsAnonymousTest() throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                return true;
            }

            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
                int statusCode = clientHttpResponse.getRawStatusCode();
                Assert.assertEquals(statusCode, 401);
            }
        });
        restTemplate.getForEntity(baseUrl, String.class);

    }

    @Test
    public void getTransactionTypeTest() throws InterruptedException {
        RestTemplate restTemplate = getBasicAuthRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/type/sandbox-payment", String.class);
        String responseText = response.getBody();
        JSONArray transactions = new JSONArray(responseText);
        Assert.assertNotNull(transactions);
        Assert.assertTrue(transactions.length() > 0);
    }

    @Test
    public void getTransactionTypeTotalAmountTest() throws InterruptedException {
        RestTemplate restTemplate = getBasicAuthRestTemplate();
        TransactionAmountResponse amountResponse = restTemplate.getForObject(baseUrl + "/type/sandbox-payment/amount", TransactionAmountResponse.class);
        Assert.assertNotNull(amountResponse);
        Assert.assertNotNull(amountResponse.getTotalAmount());
    }
}
