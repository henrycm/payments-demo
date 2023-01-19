package com.payments.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.payments.demo.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

}