package org.example.bankup.service;

import org.example.bankup.dto.customer.CreateCustomerDto;
import org.example.bankup.dto.customer.LoginCustomerDto;
import org.example.bankup.dto.customer.ViewCustomerDto;
import org.example.bankup.dto.zip_code.ResponseZipCodeDto;
import org.example.bankup.entity.Customer;
import org.example.bankup.entity.ZipCode;
import org.example.bankup.mapper.CustomerMapper;
import org.example.bankup.mapper.ZipCodeMapper;
import org.example.bankup.repository.CustomerRepository;
import org.example.bankup.repository.ZipCodeRepository;
import org.example.bankup.security.JwtUtils;
import org.example.bankup.security.RsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final RsaService rsaService;

    private final CustomerRepository customerRepository;

    private final JwtUtils jwtUtils;

    private final ZipCodeService zipCodeService;

    private final ZipCodeRepository zipCodeRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, RsaService rsaService, JwtUtils jwtUtils, ZipCodeService zipCodeService, ZipCodeRepository zipCodeRepository) {
        this.customerRepository = customerRepository;
        this.rsaService = rsaService;
        this.jwtUtils = jwtUtils;
        this.zipCodeService = zipCodeService;
        this.zipCodeRepository = zipCodeRepository;
    }

    public Customer createCustomer(CreateCustomerDto createCustomerDto) {
        String encryptedPassword = rsaService.encryptData(createCustomerDto.password());

        Customer customer = CustomerMapper.INSTANCE.createCustomerDtoToCustomer(createCustomerDto);
        customer.setPassword(encryptedPassword);

        customerRepository.save(customer);

        return customer;
    }

    public ViewCustomerDto getCustomerById(long id) {
        Optional<Customer> customerEntity = customerRepository.findFirstByCustomerId(id);

        return customerEntity
                .map(CustomerMapper.INSTANCE::customerToViewCustomerDto)
                .orElse(null);
    }

    public String loginCustomer(LoginCustomerDto loginCustomerDto) {
        Optional<Customer> customer = customerRepository.findFirstByMail(loginCustomerDto.mail().toLowerCase());

        return customer.filter( value -> rsaService.decryptData(value.getPassword()).equals(loginCustomerDto.password()))
                .map(jwtUtils::generateToken).orElse(null);
    }

    public List<ViewCustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(CustomerMapper.INSTANCE::customerToViewCustomerDto).toList();
    }

    public String updateCustomer(Customer customerUpdate){
        Optional<Customer> customer = customerRepository.findFirstByCustomerId(customerUpdate.getCustomerId());

        if(customer.isEmpty()){
            return "this customer does not exist";
        }

        customerRepository.save(customerUpdate);

        return "this customer has been updated";
    }

    public ViewCustomerDto deleteCustomerById(long id) {
        Optional<Customer> customer = customerRepository.findFirstByCustomerId(id);

        if(customer.isEmpty()){
            return null;
        }

        customerRepository.deleteById(id);

        return CustomerMapper.INSTANCE.customerToViewCustomerDto(customer.get());
    }

    public ZipCode zipCode(String country, String zipCode){
        ResponseZipCodeDto resZipCode = zipCodeService.searchZipCodeByCodes(country, zipCode);

        ZipCode zip = new ZipCode(
                resZipCode.postCode(),
                resZipCode.country(),
                resZipCode.countryAbbreviation(),
                resZipCode.places().get(0).state,
                resZipCode.places().get(0).stateAbbreviation,
                resZipCode.places().get(0).city,
                resZipCode.places().get(0).latitude,
                resZipCode.places().get(0).longitude
        );

        return zip;
    }
}