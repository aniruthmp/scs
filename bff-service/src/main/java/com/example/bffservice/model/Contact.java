package com.example.bffservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

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