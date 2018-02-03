package com.example.account.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;

    private long accountNumber;
    private int customerNumber;
    private long accountBalance;

    public Account(long accountNumber, int customerNumber, long accountBalance) {
        this.accountNumber = accountNumber;
        this.customerNumber = customerNumber;
        this.accountBalance = accountBalance;
    }
}
