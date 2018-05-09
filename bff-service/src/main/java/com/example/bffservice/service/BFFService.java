package com.example.bffservice.service;

import com.example.bffservice.model.Account;
import com.example.bffservice.model.Contact;
import com.example.bffservice.model.Customer;
import com.example.bffservice.model.UIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class BFFService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TokenService tokenService;

    @Value("${CUSTOMER_SERVICE_URL}")
    private String URL_CUSTOMER_SERVICE;

    @Value("${ACCOUNT_SERVICE_URL}")
    private String URL_ACCOUNT_SERVICE;


    public UIResponse findCustomerDetailsByNumber(int customerNumber) {
        log.info("Came inside findCustomerDetailsByNumber for customerId: " + customerNumber);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        UIResponse uiResponse = null;

        log.info("Querying the customer microservice...");
        HttpHeaders httpHeaders = tokenService.getHeadersWithAccessToken();
        HttpEntity<String> entity = new HttpEntity<String>("parameters", httpHeaders);

        String uri = URL_CUSTOMER_SERVICE + customerNumber;
        log.info("Calling URI: " + uri);
        ResponseEntity<Customer> customerResponseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, entity, Customer.class);
        stopWatch.stop();

        if (Objects.nonNull(customerResponseEntity)) {
            Customer customer = customerResponseEntity.getBody();
            log.info("customer micro-service took " + stopWatch.getTotalTimeMillis() + " milliseconds");
            log.info("customer micro-service returned " + customer.toString());

            uiResponse = new UIResponse(customer.getFirstName(), customer.getLastName(), customer.getAccountNumber());
            uiResponse.setContact(new Contact(customer.getCellPhone(), customer.getLandLine(),
                    customer.getStreetAddress(), customer.getCity(), customer.getState(), customer.getZip()));

            //Now make Account service call
            stopWatch = new StopWatch();
            stopWatch.start();
            uri = URL_ACCOUNT_SERVICE + customerNumber;
            log.info("Calling URI: " + uri);
            ResponseEntity<List<Account>> accountResponseEntity = restTemplate.exchange(
                    uri, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Account>>() {});
            stopWatch.stop();
            log.info("account micro-service took " + stopWatch.getTotalTimeMillis() + " milliseconds");

            if (Objects.nonNull(accountResponseEntity)) {
                List<Account> accounts = accountResponseEntity.getBody();
                log.info("account micro-service returned " + accounts.toString());
                uiResponse.setAccounts(accounts);
            }
        }

        return uiResponse;
    }
}
