package org.example.bankup.service;

import org.example.bankup.constants.CustomerRole;
import org.example.bankup.dto.customer.CreateCustomerDto;
import org.example.bankup.entity.Customer;
import org.example.bankup.repository.CustomerRepository;
import org.example.bankup.security.JwtUtils;
import org.example.bankup.security.RsaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private RsaService rsaService;

    private AutoCloseable autoCloseable;
    private Customer customer;
    private CreateCustomerDto customerDto;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);


        customer = new Customer("John", "john@m.com", "John",
                "Brazil", "SP", "sp", CustomerRole.STANDARD_CUSTOMER);
        customerDto = new CreateCustomerDto("John", "john@m.com", "John",
                "Brazil", "SP", "sp", CustomerRole.STANDARD_CUSTOMER);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testFindCustomer_Founded() {

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(rsaService.encryptData(anyString())).thenReturn(anyString());

        Customer createdCustomer = customerService.createCustomer(customerDto);

        assertNotNull(createdCustomer);
        assertEquals(customer.getName(), createdCustomer.getName());
        assertEquals(customer.getMail(), createdCustomer.getMail());

        verify(customerRepository).save(any(Customer.class));
        verify(rsaService).encryptData(anyString());
    }

    @Test
    void testCreateCustomer() {

        CreateCustomerDto createCustomerDto = new CreateCustomerDto("John", "john@m.com", "John",
                "Brazil", "SP", "sp", CustomerRole.STANDARD_CUSTOMER);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(rsaService.encryptData(anyString())).thenReturn(anyString());

        customerService.createCustomer(createCustomerDto);
        verify(customerRepository).save(any(Customer.class));



    }

}