package com.example.customer.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * Created by aniruthmp on 7/4/2017.
 */
@Data
@NoArgsConstructor
public class Account {
    private String accountId;
    private long accountBalance;
}
