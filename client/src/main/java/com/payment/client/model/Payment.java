package com.payment.client.model;

public class Payment {

    private String clientIdempotentKey;

    private Currency currency;

    private Float amount;

    private AccountHolder originator;

    private AccountHolder beneficiary;

    private Account sender;

    private Account receiver;

    private PaymentStatus status;


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
}
