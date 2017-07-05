package com.example.account.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * Created by aniruthmp on 7/4/2017.
 */
@Data
@NoArgsConstructor
public class Account {
    @Id
    private String accountId;

    private int customerNumber;
    private long accountBalance;

    public Account(int customerNumber, long accountBalance) {
        this.customerNumber = customerNumber;
        this.accountBalance = accountBalance;
    }
}
