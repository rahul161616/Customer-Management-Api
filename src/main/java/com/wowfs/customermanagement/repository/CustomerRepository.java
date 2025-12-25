package com.wowfs.customermanagement.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wowfs.customermanagement.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //Custom method to check for duplicate emails
    Optional<Customer> findByEmail(String email);
}