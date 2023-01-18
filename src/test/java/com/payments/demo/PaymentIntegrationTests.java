package com.payments.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payments.demo.model.AccountType;
import com.payments.demo.model.AccountHolder;
import com.payments.demo.model.Currency;
import com.payments.demo.model.Payment;
import com.payments.demo.model.PaymentStatus;
import com.payments.demo.model.Account;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PaymentIntegrationTests {
    private final static Logger LOGGER = LoggerFactory.getLogger(PaymentIntegrationTests.class);

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PaymentRepository repo;

    @Test
    void testPaymentCreated() throws Exception{
        Payment p = new Payment();
        p.setAmount(100);
        p.setBeneficiary(new AccountHolder(1, "Ben"));
        p.setCurrency(Currency.USD);
        p.setId(UUID.randomUUID().toString());
        p.setOriginator(new AccountHolder(1, "Bob"));
        p.setReceiver(new Account(AccountType.SAVINGS, 1005555551));
        p.setSender(new Account(AccountType.SAVINGS, 1005555552));
        p.setStatus(PaymentStatus.SENT);

        LOGGER.info(new ObjectMapper().writeValueAsString(p));

        ResponseEntity<Void> resp = restTemplate.postForEntity("/payments", p, Void.class);
        assertEquals(HttpStatus.ACCEPTED, resp.getStatusCode());
    }
}
