package com.payments.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.payments.demo.model.Payment;

import jakarta.annotation.Resource;

@RestController
public class PaymentController {

    @Resource
    private PaymentService service;

    @PostMapping("/payments")
    ResponseEntity<Void> create(@RequestBody Payment payment) {
        service.create(payment);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}
