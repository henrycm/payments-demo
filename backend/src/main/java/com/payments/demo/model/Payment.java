package com.payments.demo.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    @NotNull
    private String clientIdempotentKey;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Currency currency;

    @NotNull
    private Float amount;

    @ManyToOne()
    @NotNull
    private AccountHolder originator;

    @ManyToOne()
    @NotNull
    private AccountHolder beneficiary;

    @ManyToOne()
    @NotNull
    private Account sender;

    @ManyToOne()
    @NotNull
    private Account receiver;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public AccountHolder getOriginator() {
        return originator;
    }

    public void setOriginator(AccountHolder originator) {
        this.originator = originator;
    }

    public AccountHolder getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(AccountHolder beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getClientIdempotentKey() {
        return clientIdempotentKey;
    }

    public void setClientIdempotentKey(String clientIdempotentKey) {
        this.clientIdempotentKey = clientIdempotentKey;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
