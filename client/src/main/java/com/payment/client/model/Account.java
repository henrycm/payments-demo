package com.payment.client.model;

public class Account {

    private AccountType accountType;

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
}
