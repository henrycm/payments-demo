package com.payments.demo.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Payment {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private float amount;
    @ManyToOne
    private AccountHolder originator;
    @ManyToOne
    private AccountHolder beneficiary;
    @ManyToOne
    private Account sender;
    @ManyToOne
    private Account receiver;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public float getAmount() {
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
