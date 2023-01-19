package com.payment.client;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.payment.client.model.Payment;


@Service
public class ClientService {
    private static Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    static RestTemplate restTemplate = new RestTemplate();

    @Value(value = "${paymentservice.endpoint}")
    private String endpoint;

    boolean createPayment(Payment payment) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON.toString());

        String uuid = UUID.randomUUID().toString();
        LOGGER.info("Creating payment: " + uuid);

        payment.setClientIdempotentKey(uuid);
        HttpEntity<Payment> request = new HttpEntity<Payment>(payment, headers);
        ResponseEntity<Void> response = restTemplate.exchange(endpoint, HttpMethod.POST, request, Void.class);

        if (response.getStatusCode() == HttpStatus.ACCEPTED) {
            LOGGER.info("Payment created:" + uuid);
        } else {
            LOGGER.info("Error creating payment:" + response.toString());
            return false;
        }

        return true;
    }
}
