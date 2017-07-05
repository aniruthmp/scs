package com.example.account.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Account {
    @Id
    private String accountId;

    private long accountNumber;
    private int customerNumber;
    private long accountBalance;

    public Account(long accountNumber, int customerNumber, long accountBalance) {
        this.accountNumber = accountNumber;
        this.customerNumber = customerNumber;
        this.accountBalance = accountBalance;
    }
}
