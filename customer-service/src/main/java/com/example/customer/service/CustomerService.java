package com.example.customer.service;

import com.example.customer.domain.Customer;
import com.example.customer.ipc.AccountClient;
import com.example.customer.model.Account;
import com.example.customer.model.Contact;
import com.example.customer.model.CustomerResponse;
import com.example.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
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

}
