package com.example.customer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by aniruthmp on 7/4/2017.
 */
@Data
@AllArgsConstructor
public class Contact {
    private String cellPhone;
    private String landLine;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
}
