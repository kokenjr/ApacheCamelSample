package com.backbase.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.Charset;
import java.util.Base64;

/**
 * Created by korji on 7/14/17.
 */
public class AuthProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String authorization = exchange.getIn().getHeader("Authorization", String.class);
        if (authorization != null && authorization.startsWith("Basic")) {
            String base64Credentials = authorization.substring("Basic" .length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials),
                    Charset.forName("UTF-8"));
            final String[] tokens = credentials.split(":", 2);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(tokens[0], tokens[1]);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
}
