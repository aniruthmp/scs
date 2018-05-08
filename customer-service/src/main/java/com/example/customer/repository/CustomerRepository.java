package com.example.customer.repository;

import com.example.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByLastName(@Param("name") String name);
    List<Customer> findByAccountNumber(@Param("accountNumber") int accountNumber);
    Customer findByCustomerNumber(@Param("customerNumber") int customerNumber);
}
