package com.example.account.repository;

import com.example.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "account", path = "account")
public interface AccountRepository extends JpaRepository<Account, String> {

    List<Account> findByCustomerNumber(@Param("customerNumber") int customerNumber);
}
