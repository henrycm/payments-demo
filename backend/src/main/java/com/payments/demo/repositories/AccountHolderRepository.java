package com.payments.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.payments.demo.model.AccountHolder;

public interface AccountHolderRepository extends CrudRepository<AccountHolder, Long> {

}
