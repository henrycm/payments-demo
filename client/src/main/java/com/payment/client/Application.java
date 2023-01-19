package com.payment.client;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.payment.client.model.Account;
import com.payment.client.model.AccountHolder;
import com.payment.client.model.AccountType;
import com.payment.client.model.Currency;
import com.payment.client.model.Payment;
import com.payment.client.model.PaymentStatus;

import jakarta.annotation.Resource;

@SpringBootApplication
public class Application implements ApplicationRunner {

	@Value(value = "${numPayments:1}")
	int numPayments = 1;

	@Resource
	ClientService service;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		for (long i = 0; i < numPayments; i++) {
			Payment p = build();
			p.setAmount(p.getAmount() + i);
			p.setBeneficiary(new AccountHolder(i, "Ben" + i));
			p.setReceiver(new Account(AccountType.SAVINGS, 1005555551L + i));
			service.createPayment(p);
		}
	}

	private static Payment build() {
		Payment p = new Payment();
		p.setAmount(100);
		p.setCurrency(Currency.USD);
		p.setClientIdempotentKey(UUID.randomUUID().toString());
		p.setOriginator(new AccountHolder(1L, "Bob"));
		p.setSender(new Account(AccountType.SAVINGS, 1005555552L));
		p.setStatus(PaymentStatus.SENT);

		return p;
	}
}
