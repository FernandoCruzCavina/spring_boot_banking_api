package org.example.bankup.repository;

import org.example.bankup.constants.CustomerRole;
import org.example.bankup.entity.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("John", "john@m.com", "John", "Brazil", "SP", "sp", CustomerRole.STANDARD_CUSTOMER);
        customerRepository.save(customer);
    }

    @AfterEach
    void tearDown() {
        customer = null;
        customerRepository.deleteAll();
    }

    @Test
    void testFindByCustomer_Found(){
        Optional<Customer> customerFounded = customerRepository.findFirstByMail("john@m.com");

        assertTrue(customerFounded.isPresent());
    }

    @Test
    void testFindByCustomer_NotFound(){
        Optional<Customer> customerNotFounded = customerRepository.findFirstByMail("john@m.com");

        assertTrue(customerNotFounded.isPresent());
    }
}