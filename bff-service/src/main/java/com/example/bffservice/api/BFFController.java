package com.example.bffservice.api;

import com.example.bffservice.model.UIResponse;
import com.example.bffservice.service.BFFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BFFController {

    @Autowired
    BFFService bffService;

    @GetMapping(value = "/customer")
    public UIResponse getByCustomerNumber(@RequestParam("customerNumber") int customerNumber){
        return bffService.findCustomerDetailsByNumber(customerNumber);
    }
}
