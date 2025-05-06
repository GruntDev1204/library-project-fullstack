package com.library_management.api.repository;

import com.library_management.api.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
}
