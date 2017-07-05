package com.example.customer.model;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by aniruthmp on 7/5/2017.
 */
@Data
@ToString
public class CustomerResponse {

    private Collection<Account> accounts = new ArrayList<>();
    private Contact contact;
    private String customerName;
    private int customerNumber;

    public CustomerResponse(String firstName, String lastName, int customerNumber) {
        this.customerName = StringUtils.join(firstName, " ", lastName);
        this.customerNumber = customerNumber;
    }
}
