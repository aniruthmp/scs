package com.example.account.api;

import com.example.account.domain.Account;
import com.example.account.repository.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
public class AccountController {

    private AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping(value = "/accounts/customer")
    public List<Account> getByCustomerNumber(@RequestParam("customerNumber") int customerNumber) {
        Random r = new Random();
        int rr = r.nextInt(4);
        if (rr == 1) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.accountRepository.findByCustomerNumber(customerNumber);
    }

}
