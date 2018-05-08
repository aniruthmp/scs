package com.example.customer.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by aniruthmp on 7/4/2017.
 */
@Data
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Customer(int accountNumber, int customerNumber,
                    String firstName, String lastName, String cellPhone, String landLine,
                    String streetAddress, String city, String state, String zip) {
        this.accountNumber = accountNumber;
        this.customerNumber = customerNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellPhone = cellPhone;
        this.landLine = landLine;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

}
