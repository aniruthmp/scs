package com.example.account.job;

import com.example.account.domain.Account;
import com.example.account.repository.AccountRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Date;

/**
 * Created by a.c.parthasarathy
 */
@Component
@Slf4j
public class ScheduledJob  implements CommandLineRunner {
    private final int MAX_RECORDS = 100;
    private AccountRepository accountRepository;

    public ScheduledJob(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("prepareDummyRecords started @ ", new Date());

        try {
            accountRepository.deleteAll();
            log.info("Database cleared");
            StopWatch stopWatch = new StopWatch("Stop Watch for ScheduledJob");
            stopWatch.start();

            log.info("Dummy records getting inserted..");
            for (int i = 0; i < MAX_RECORDS; i++) {
                Faker faker = new Faker();
                Account account = new Account(faker.number().numberBetween(100, 200),
                        faker.number().numberBetween(9999L, 99999L));
                accountRepository.save(account);
                if (i % 10 == 0) {
                    log.info("Inserted records count: " + i);
                }
            }
            stopWatch.stop();
            log.info(stopWatch.prettyPrint());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
