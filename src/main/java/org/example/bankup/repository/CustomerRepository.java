package org.example.bankup.repository;

import org.example.bankup.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findFirstByCustomerId(long id);
    Optional<Customer> findFirstByMail(String mail);
}
