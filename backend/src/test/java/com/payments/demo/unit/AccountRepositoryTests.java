package com.payments.demo.unit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.payments.demo.model.Account;
import com.payments.demo.model.AccountType;
import com.payments.demo.repositories.AccountRepository;

import jakarta.annotation.Resource;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccountRepositoryTests {

    @Resource
    private AccountRepository repo;

    @Test
    void testCreate() {
        long id = new Random().nextLong();
        repo.save(new Account(AccountType.SAVINGS, id));
        assertTrue(repo.findById(id).isPresent());
    }

    @Test
    void testNullType() {
        assertThrows(RuntimeException.class, () -> {
            repo.save(new Account(null, new Random().nextLong()));
        });
    }

    @Test
    void testNullAccount() {
        assertThrows(RuntimeException.class, () -> {
            repo.save(new Account(AccountType.CHECKING, null));
        });
    }
}
