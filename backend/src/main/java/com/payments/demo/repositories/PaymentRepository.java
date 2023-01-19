package com.payments.demo.repositories;

import com.payments.demo.model.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

    Payment findByClientIdempotentKey(String key);
}
