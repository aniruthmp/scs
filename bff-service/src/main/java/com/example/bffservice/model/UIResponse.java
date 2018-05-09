package com.example.bffservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class UIResponse {
    private Collection<Account> accounts = new ArrayList<>();
    private Contact contact;
    private String customerName;
    private int customerNumber;

    public UIResponse(String firstName, String lastName, int customerNumber) {
        this.customerName = StringUtils.join(firstName, " ", lastName);
        this.customerNumber = customerNumber;
    }
}
