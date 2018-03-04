package com.example.customer.ipc;

import com.example.customer.model.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("account-service")
public interface AccountClient {

    @RequestMapping(method = RequestMethod.GET, value = "/accounts/search/findByCustomerNumber?customerNumber={customerNumber}")
    Resources<Account> getAccountsResources(@PathVariable("customerNumber") int customerNumber);

    @RequestMapping(method = RequestMethod.GET, value = "/accounts/customer?customerNumber={customerNumber}")
    List<Account> getAccounts(@PathVariable("customerNumber") int customerNumber);

    @RequestMapping(method = RequestMethod.GET, value = "/accounts")
    PagedResources<Account> getPagedAccounts(@RequestParam("page") int page, @RequestParam("size") int size,
                                             @RequestParam(value = "sort", required = false) Sort sort);
}
