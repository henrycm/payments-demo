package com.payments.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payments.demo.model.Payment;
import com.payments.demo.model.PaymentStatus;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

@Component
public class PaymentService {
    private final static Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    @Resource
    private PaymentRepository repo;
    @Resource
    ObjectMapper objectMapper;
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${kafka.topic.name}")
    String kafkaTopicName;

    @Transactional
    void create(Payment payment) {
        Payment existing = repo.findByClientIdempotentKey(payment.getClientIdempotentKey());
        if (existing != null) {
            LOGGER.info("Payment with idempotentKey %s already exists"
                    .formatted(payment.getClientIdempotentKey()));
        } else {
            LOGGER.info("Creating payment: " + payment);
            payment.setStatus(PaymentStatus.CREATED);
            repo.save(payment);
            try {
                kafkaTemplate.send(kafkaTopicName, objectMapper.writeValueAsString(payment)).get();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    Payment retrieve(String idempotentKey){
        LOGGER.info("Retrieving payment: " + idempotentKey);
        return repo.findByClientIdempotentKey(idempotentKey);
    }
}
