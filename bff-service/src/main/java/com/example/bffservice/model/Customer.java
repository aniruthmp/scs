package com.example.bffservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by aniruthmp on 7/4/2017.
 */
@Data
@NoArgsConstructor
public class Customer {
    private long customerId;

    private int accountNumber;
    private int customerNumber;
    private String firstName;
    private String lastName;
    private String cellPhone;
    private String landLine;

    private String streetAddress;
    private String city;
    private String state;
    private String zip;

}
