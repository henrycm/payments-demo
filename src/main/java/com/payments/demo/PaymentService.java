package com.payments.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.payments.demo.model.Payment;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

@Component
public class PaymentService {
    private final static Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    @Resource
    private PaymentRepository repo;

    @Transactional
    void create(Payment payment) {
        LOGGER.info("Creating payment " + payment);
        repo.save(payment);
    }
}
