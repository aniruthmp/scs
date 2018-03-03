package com.example.customer.job;

import com.example.customer.domain.Customer;
import com.example.customer.repository.CustomerRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Date;
import java.util.Objects;

/**
 * Created by a.c.parthasarathy
 */
@Component
@Slf4j
public class ScheduledJob implements CommandLineRunner {
    private final int MAX_RECORDS = 150;
    private CustomerRepository customerRepository;

    public ScheduledJob(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("prepareDummyRecords started @ ", new Date());

        try {
            customerRepository.deleteAll();
            log.info("Database cleared");
            StopWatch stopWatch = new StopWatch("Stop Watch for ScheduledJob");
            stopWatch.start();

            log.info("Dummy records getting inserted..");
            for (int i = 0; i < MAX_RECORDS; i++) {
                Faker faker = new Faker();
                int customerNumber = faker.number().numberBetween(100, 200);

                if (Objects.nonNull(customerRepository.findByCustomerNumber(customerNumber))) {
//                    log.warn("customerNumber: " + customerNumber + " is already present");
                    continue;
                }

                Customer customer = new Customer(faker.number().numberBetween(100, 200), customerNumber,
                        faker.name().firstName(), faker.name().lastName(),
                        faker.phoneNumber().cellPhone(), faker.phoneNumber().phoneNumber(),
                        faker.address().streetAddress(), faker.address().city(),
                        faker.address().state(), faker.address().zipCode());
                customerRepository.save(customer);
//                if (i % 10 == 0)
//                    log.info("Inserted records count: " + i);
            }
            log.info(stopWatch.prettyPrint());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
