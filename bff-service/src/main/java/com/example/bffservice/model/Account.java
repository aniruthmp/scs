package com.example.bffservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {
    private long accountNumber;
    private long accountBalance;
}
