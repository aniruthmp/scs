package com.example.customer.ipc;

import com.example.customer.model.Account;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("account-service")
public interface AccountClient {

    @RequestMapping(method = RequestMethod.GET, value = "/accounts/search/findByCustomerNumber?customerNumber={customerNumber}")
    Resources<Account> getAccounts(@PathVariable("customerNumber") int customerNumber);
}
