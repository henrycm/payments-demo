package com.payments.demo.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Account {
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Id
    private Long accountNumber;

    public Account(){}

    public Account(AccountType accountType, Long accountNumber) {
        this.accountType = accountType;
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccounNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
