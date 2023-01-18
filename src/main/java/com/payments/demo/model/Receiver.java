package com.payments.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Receiver {
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Id
    private long accountNumber;

    public Receiver(){}

    public Receiver(AccountType accountType, long accountNumber) {
        this.accountType = accountType;
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccounNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }
}
