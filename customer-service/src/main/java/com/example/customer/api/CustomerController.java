package com.example.customer.api;

import com.example.customer.model.Account;
import com.example.customer.model.CustomerResponse;
import com.example.customer.service.CustomerService;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping(value = "/details")
    public CustomerResponse getByCustomerNumber(@RequestParam("customerNumber") int customerNumber){
        return this.customerService.findCustomerDetailsByNumber(customerNumber);
    }

    @GetMapping(value = "/latest")
    public PagedResources<Account> getPagedAccounts(Pageable pageable){
        return this.customerService.getPagedAccounts(pageable);
    }

}
