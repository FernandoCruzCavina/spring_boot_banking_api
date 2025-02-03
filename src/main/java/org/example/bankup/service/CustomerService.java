package org.example.bankup.service;

import org.example.bankup.dto.customer.CreateCustomerDto;
import org.example.bankup.dto.customer.LoginCustomerDto;
import org.example.bankup.dto.customer.ViewCustomerDto;
import org.example.bankup.entity.Customer;
import org.example.bankup.repository.CustomerRepository;
import org.example.bankup.security.JwtUtils;
import org.example.bankup.security.RsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final RsaService rsaService;

    private final CustomerRepository customerRepository;

    private final JwtUtils jwtUtils;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, RsaService rsaService, JwtUtils jwtUtils) {
        this.customerRepository = customerRepository;
        this.rsaService = rsaService;
        this.jwtUtils = jwtUtils;
    }

    public void createCustomer(CreateCustomerDto createCustomerDto) {
        String encryptedPassword = rsaService.encryptData(createCustomerDto.getPassword());

        Customer customer = new Customer(
                createCustomerDto.getName(),
                createCustomerDto.getMail(),
                encryptedPassword,
                createCustomerDto.getCountry(),
                createCustomerDto.getState(),
                createCustomerDto.getCity(),
                createCustomerDto.getCustomerRole()
        );

        customerRepository.save(customer);
    }

    public ViewCustomerDto getCustomerById(long id) {
        Optional<Customer> customerEntity = customerRepository.findFirstByCustomerId(id);

        return customerEntity
                .map(c -> new ViewCustomerDto(
                    c.getCustomerId(),
                    c.getName(),
                    c.getMail(),
                    c.getCountry(),
                    c.getState(),
                    c.getCity(),
                    c.getCustomerRole()))
                .orElse(null);
    }

    public String loginCustomer(LoginCustomerDto loginCustomerDto) {
        Optional<Customer> customer = customerRepository.findFirstByMail(loginCustomerDto.getMail().toLowerCase());

        return customer.filter( value -> rsaService.decryptData(value.getPassword()).equals(loginCustomerDto.getPassword()))
                .map(jwtUtils::generateToken).orElse(null);
    }
}