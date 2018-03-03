package com.example.customer.service;

import com.example.customer.domain.Customer;
import com.example.customer.ipc.AccountClient;
import com.example.customer.model.Account;
import com.example.customer.model.Contact;
import com.example.customer.model.CustomerResponse;
import com.example.customer.repository.CustomerRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.util.Collection;
import java.util.Objects;

@Service
@Slf4j
public class CustomerService {

    private CustomerRepository customerRepository;
    private AccountClient accountClient;

    public CustomerService(CustomerRepository customerRepository, AccountClient accountClient) {
        this.customerRepository = customerRepository;
        this.accountClient = accountClient;
    }

    @HystrixCommand(fallbackMethod = "getDummyCustomer")
    public CustomerResponse findCustomerDetailsByNumber(int customerNumber) {
        log.info("Came inside findCustomerDetailsById for customerId: " + customerNumber);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CustomerResponse customerResponse = null;

        log.info("Querying the database...");
        Customer customer = this.customerRepository.findByCustomerNumber(customerNumber);

        if (Objects.isNull(customer))
            return null;

        customerResponse = new CustomerResponse(customer.getFirstName(), customer.getLastName(), customerNumber);
        customerResponse.setContact(new Contact(customer.getCellPhone(), customer.getLandLine(),
                customer.getStreetAddress(), customer.getCity(), customer.getState(), customer.getZip()));

        log.info("Invoking account-service feign call...");
        Collection<Account> accounts = this.accountClient.getAccounts(customerNumber);
        log.info("Got the response from account-service feign call");

        if (!CollectionUtils.isEmpty(accounts))
            customerResponse.setAccounts(accounts);

        stopWatch.stop();
        log.info("findCustomerDetailsById for customerId: " + customerNumber +
                " took " + stopWatch.getTotalTimeMillis() + " milliseconds");

        return customerResponse;
    }

    /**
     * Fallback method for @method findCustomerDetailsByNumber
     *
     * @param customerNumber customerNumber
     * @return Dummy customer response
     */
    CustomerResponse getDummyCustomer(int customerNumber) {
        return new CustomerResponse("Dummy", "Customer", customerNumber);
    }

    public PagedResources<Account> getPagedAccounts(Pageable pageable) {

        log.info("pageable values: " + pageable.toString());
        PagedResources<Account> pagedAccounts = accountClient.getPagedAccounts(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        log.info("From FeignClient: " + pagedAccounts.toString());
        log.info("List: " + pagedAccounts.getContent().toString());
        return pagedAccounts;
    }
}
