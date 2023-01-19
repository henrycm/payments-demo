package com.payments.demo.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.payments.demo.PaymentService;
import com.payments.demo.model.Account;
import com.payments.demo.model.AccountHolder;
import com.payments.demo.model.AccountType;
import com.payments.demo.model.Currency;
import com.payments.demo.model.Payment;
import com.payments.demo.model.PaymentStatus;

import jakarta.annotation.Resource;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PaymentIntegrationTests {
    @Value(value = "${local.server.port}")
    private int port;

    @Resource
    private TestRestTemplate restTemplate;

    @Resource
    private PaymentService service;

    @Test
    void testPaymentCreated() throws Exception {
        Payment p = build();
        ResponseEntity<Void> resp = restTemplate.postForEntity("/payments", p, Void.class);
        assertEquals(HttpStatus.ACCEPTED, resp.getStatusCode());
        assertNotNull(service.retrieve(p.getClientIdempotentKey()));
    }

    @Test
    void testQueryStatus() {
        Payment p = build();
        service.create(p);
        ResponseEntity<JsonNode> resp = restTemplate.getForEntity("/payments/" + p.getClientIdempotentKey(),
                JsonNode.class);
        assertEquals(HttpStatus.OK, resp.getStatusCode());

        assertEquals("CREATED", resp.getBody().get("status").asText());
    }

    private Payment build() {
        Payment p = new Payment();
        p.setAmount(100);
        p.setBeneficiary(new AccountHolder(1L, "Ben"));
        p.setCurrency(Currency.USD);
        p.setClientIdempotentKey(UUID.randomUUID().toString());
        p.setOriginator(new AccountHolder(1L, "Bob"));
        p.setReceiver(new Account(AccountType.SAVINGS, 1005555551L));
        p.setSender(new Account(AccountType.SAVINGS, 1005555552L));
        p.setStatus(PaymentStatus.SENT);

        return p;
    }
}
