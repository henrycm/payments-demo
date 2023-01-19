package com.payments.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payments.demo.model.Payment;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Resource
    private PaymentService service;

    @PostMapping()
    ResponseEntity<Void> acept(@RequestBody Payment payment) {
        service.create(payment);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{idempotentKey}")
    PaymentStatusResponse query(@PathVariable String idempotentKey) {
        Payment payment = service.retrieve(idempotentKey);
        return new PaymentStatusResponse(payment.getStatus().name());
    }
}
