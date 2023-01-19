package com.payment.client;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

@SpringBootApplication
public class Application implements ApplicationRunner {
	private static Logger LOGGER = LoggerFactory.getLogger(Application.class);

	@Value(value = "${paymentservice.endpoint}")
	private String endpoint;

	@Value(value = "${payload}")
	private String payload;

	@Value(value = "${numPayments}")
	private int numPayments = 1;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if(payload == null){
			LOGGER.error("Expected payload as parameter: --payload='{}'");
		}

		LOGGER.info("Excecuting payments client");
		for (int i = 0; i < numPayments; i++) {
			String uuid = UUID.randomUUID().toString();
			LOGGER.info("Creating payment: " + uuid);
			ObjectNode node = (ObjectNode) new ObjectMapper().readTree(payload);
			node.put("clientIdempotentKey", uuid);
			LOGGER.info(node.toString());

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", MediaType.APPLICATION_JSON.toString());

			HttpEntity<JsonNode> request = new HttpEntity<JsonNode>(node, headers);
			ResponseEntity<Void> response = restTemplate.exchange(endpoint, HttpMethod.POST, request, Void.class);

			if (response.getStatusCode() == HttpStatus.ACCEPTED) {
				LOGGER.info("Payment created:" + node.get("clientIdempotentKey").asText());
			}
		}

	}
}
