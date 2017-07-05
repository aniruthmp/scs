package com.example.customer.api;

import com.example.customer.model.CustomerResponse;
import com.example.customer.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping(value = "/customer")
    public CustomerResponse getByCustomerNumber(@RequestParam("customerNumber") int customerNumber){
        return this.customerService.findCustomerDetailsByNumber(customerNumber);
    }

}
