package com.example.account.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
