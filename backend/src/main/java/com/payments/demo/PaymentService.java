package com.payments.demo;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payments.demo.model.Account;
import com.payments.demo.model.AccountHolder;
import com.payments.demo.model.Payment;
import com.payments.demo.model.PaymentStatus;
import com.payments.demo.repositories.AccountRepository;
import com.payments.demo.repositories.AccountHolderRepository;
import com.payments.demo.repositories.PaymentRepository;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

@Component
public class PaymentService {
    private final static Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    @Resource
    private PaymentRepository paymentRepo;
    @Resource
    private AccountHolderRepository accounHolderRepo;
    @Resource
    private AccountRepository accountRepo;

    @Resource
    ObjectMapper objectMapper;
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${kafka.topic.name}")
    String kafkaTopicName;

    @Transactional
    public void create(Payment payment) {
        Payment existing = paymentRepo.findByClientIdempotentKey(payment.getClientIdempotentKey());
        if (existing != null) {
            LOGGER.info("Payment with idempotentKey %s already exists"
                    .formatted(payment.getClientIdempotentKey()));
        } else {
            LOGGER.info("Creating payment: " + payment.getClientIdempotentKey());

            Optional<AccountHolder> existingOriginator = accounHolderRepo.findById(payment.getOriginator().getId());
            if (existingOriginator.isEmpty()) {
                accounHolderRepo.save(payment.getOriginator());
            }

            Optional<AccountHolder> existingBeneficiary = accounHolderRepo.findById(payment.getBeneficiary().getId());
            if (existingBeneficiary.isEmpty()) {
                accounHolderRepo.save(payment.getBeneficiary());
            }

            Optional<Account> existingSender = accountRepo.findById(payment.getSender().getAccountNumber());
            if (existingSender.isEmpty()) {
                accountRepo.save(payment.getSender());
            }

            Optional<Account> existingReceiver = accountRepo.findById(payment.getReceiver().getAccountNumber());
            if (existingReceiver.isEmpty()) {
                accountRepo.save(payment.getReceiver());
            }

            payment.setStatus(PaymentStatus.CREATED);
            paymentRepo.save(payment);
            try {
                kafkaTemplate.send(kafkaTopicName, objectMapper.writeValueAsString(payment)).get();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public Payment retrieve(String idempotentKey) {
        LOGGER.info("Retrieving payment: " + idempotentKey);
        return paymentRepo.findByClientIdempotentKey(idempotentKey);
    }
}
