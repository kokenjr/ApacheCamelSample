package com.backbase.camel;

import com.backbase.camel.processor.TransactionsProcessor;
import com.backbase.common.Constants;
import com.backbase.domain.Transaction;
import com.backbase.domain.TransactionAmountResponse;
import com.backbase.domain.TransactionResponse;
import com.backbase.camel.processor.AuthProcessor;
import org.apache.camel.CamelAuthorizationException;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.spring.security.SpringSecurityAccessPolicy;
import org.apache.camel.component.spring.security.SpringSecurityAuthorizationPolicy;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import static org.apache.camel.model.rest.RestParamType.path;

/**
 * Created by korji on 7/13/17.
 */
@Component
public class CamelRouteBuilder extends RouteBuilder {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccessDecisionManager accessDecisionManager;

    private static final Logger logger = LogManager.getLogger(CamelRouteBuilder.class);


    public void configure() throws Exception {
        logger.debug("----------------------------");
        logger.debug("Starting Camel Configuration");
        logger.debug("----------------------------");


        SpringSecurityAuthorizationPolicy securityAuthorizationPolicy = new SpringSecurityAuthorizationPolicy();
        securityAuthorizationPolicy.setAuthenticationManager(authenticationManager);
        securityAuthorizationPolicy.setAccessDecisionManager(accessDecisionManager);
        SpringSecurityAccessPolicy springSecurityAccessPolicy = new SpringSecurityAccessPolicy("ROLE_USER");
        securityAuthorizationPolicy.setSpringSecurityAccessPolicy(springSecurityAccessPolicy);

        restConfiguration()
                .component("jetty")
                .bindingMode(RestBindingMode.json)
                .port(8081)
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Backbase Transactions API")
                .apiProperty("api.version", "1.0.0")
                .apiProperty("cors", "true");

        onException(CamelAuthorizationException.class)
                .handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(401))
                .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
                .setBody().constant("Unauthenticated user. You shall not pass.");

        rest("/api/transactions")
                .description("Transactions REST service")
                .produces("application/json")

                .get()
                .description("Get all Transactions")
                .outType(Transaction.class)
                .route()
                .process(new AuthProcessor())
                .policy(securityAuthorizationPolicy)
                .to("direct:transactions")
                .endRest()

                .get("/type/{transactionType}")
                .description("Get all transactions, for a specific transaction type")
                .outType(Transaction.class)
                .param()
                .name("transactionType")
                .type(path)
                .description("The type of transaction")
                .dataType("string")
                .endParam()
                .route()
                .process(new AuthProcessor())
                .policy(securityAuthorizationPolicy)
                .to("bean:transactionService?method=getTransactions(${header.transactionType})")
                .endRest()

                .get("/type/{transactionType}/amount")
                .description("Get total amount, of all transactions, for a specific transaction type")
                .outType(TransactionAmountResponse.class)
                .param()
                .name("transactionType")
                .type(path)
                .description("The type of transaction")
                .dataType("string")
                .endParam()
                .route()
                .process(new AuthProcessor())
                .policy(securityAuthorizationPolicy)
                .to("bean:transactionService?method=getTransactionTotalAmount(${header.transactionType})")
                .endRest();


        /*
            NOTE: Would have liked to implement the other two endpoints like this, but for some reason HTTPS4 wasn't playing
            nice with the dynamic uri param. ¯\_(ツ)_/¯
         */
        from("direct:transactions")
                .to("https4:" + Constants.OPEN_BANK_URI + "?bridgeEndpoint=true")
                .unmarshal().json(JsonLibrary.Jackson, TransactionResponse.class)
                .process(new TransactionsProcessor());


    }
}
