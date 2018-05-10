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
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
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
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    private TokenService tokenService;

    @Value("${CUSTOMER_SERVICE_URL}")
    private String URL_CUSTOMER_SERVICE;

    @Value("${ACCOUNT_SERVICE_URL}")
    private String URL_ACCOUNT_SERVICE;

    /**
     * BackEnd For Front-End method to aggregate data from 2 micro-services
     *
     * @param customerNumber
     * @return
     */
    public UIResponse findCustomerDetailsByNumber(int customerNumber) {
        log.info("Came inside findCustomerDetailsByNumber for customerId: " + customerNumber);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        UIResponse uiResponse = null;
        HttpHeaders httpHeaders = tokenService.getHeadersWithAccessToken();
        HttpEntity<String> entity = new HttpEntity<String>("parameters", httpHeaders);

        log.info("Querying the customer microservice...");
        ResponseEntity<Customer> customerResponseEntity = restTemplate.exchange(
                URL_CUSTOMER_SERVICE, HttpMethod.GET, entity, Customer.class, customerNumber);
        stopWatch.stop();
        log.info("customer micro-service took " + stopWatch.getTotalTimeMillis() + " milliseconds");

        if (Objects.nonNull(customerResponseEntity)) {
            Customer customer = customerResponseEntity.getBody();

            uiResponse = new UIResponse(customer.getFirstName(), customer.getLastName(), customer.getAccountNumber());
            uiResponse.setContact(new Contact(customer.getCellPhone(), customer.getLandLine(),
                    customer.getStreetAddress(), customer.getCity(), customer.getState(), customer.getZip()));

            //Now make Account service call
            log.info("Querying the account microservice...");
            stopWatch = new StopWatch();
            stopWatch.start();
            ResponseEntity<List<Account>> accountResponseEntity = restTemplate.exchange(
                    URL_ACCOUNT_SERVICE, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Account>>() {
                    }, customerNumber);
            stopWatch.stop();
            log.info("account micro-service took " + stopWatch.getTotalTimeMillis() + " milliseconds");

            if (Objects.nonNull(accountResponseEntity)) {
                List<Account> accounts = accountResponseEntity.getBody();
                uiResponse.setAccounts(accounts);
            }
        }

        return uiResponse;
    }

    /**
     * BackEnd For Front-End method to aggregate data from 2 micro-services
     *
     * @param customerNumber
     * @return
     */
    public UIResponse findCustomerDetailsByNumberUsingOAuth(int customerNumber) {
        log.info("Came inside findCustomerDetailsByNumberUsingOAuth for customerId: " + customerNumber);
        UIResponse uiResponse = null;

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("Querying the customer microservice...");
        ResponseEntity<Customer> customerResponseEntity = oAuth2RestTemplate.getForEntity(
                URL_CUSTOMER_SERVICE, Customer.class, customerNumber);
        stopWatch.stop();
        log.info("customer micro-service took " + stopWatch.getTotalTimeMillis() + " milliseconds");

        if (Objects.nonNull(customerResponseEntity)) {
            Customer customer = customerResponseEntity.getBody();

            uiResponse = new UIResponse(customer.getFirstName(), customer.getLastName(), customer.getAccountNumber());
            uiResponse.setContact(new Contact(customer.getCellPhone(), customer.getLandLine(),
                    customer.getStreetAddress(), customer.getCity(), customer.getState(), customer.getZip()));

            //Now make Account service call
            log.info("Querying the account microservice...");
            stopWatch = new StopWatch();
            stopWatch.start();
            ResponseEntity<List<Account>> accountResponseEntity = oAuth2RestTemplate.exchange(
                    URL_ACCOUNT_SERVICE, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Account>>() {}, customerNumber);
            stopWatch.stop();
            log.info("account micro-service took " + stopWatch.getTotalTimeMillis() + " milliseconds");

            if (Objects.nonNull(accountResponseEntity)) {
                List<Account> accounts = accountResponseEntity.getBody();
                uiResponse.setAccounts(accounts);
            }
        }

        return uiResponse;
    }
}
