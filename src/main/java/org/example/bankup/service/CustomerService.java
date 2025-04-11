package org.example.bankup.service;

import org.example.bankup.dto.customer.CreateCustomerDto;
import org.example.bankup.dto.customer.LoginCustomerDto;
import org.example.bankup.dto.customer.ViewCustomerDto;
import org.example.bankup.dto.zip_code.ResponseZipCodeDto;
import org.example.bankup.entity.Customer;
import org.example.bankup.entity.ZipCode;
import org.example.bankup.exception.EntityNotFoundException;
import org.example.bankup.exception.UnauthorizedException;
import org.example.bankup.mapper.CustomerMapper;
import org.example.bankup.repository.CustomerRepository;
import org.example.bankup.repository.ZipCodeRepository;
import org.example.bankup.security.JwtUtils;
import org.example.bankup.security.RsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CustomerService {

    private final RsaService rsaService;

    private final CustomerRepository customerRepository;

    private final JwtUtils jwtUtils;

    private final ZipCodeService zipCodeService;

    private final ZipCodeRepository zipCodeRepository;

    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, RsaService rsaService, JwtUtils jwtUtils, ZipCodeService zipCodeService, ZipCodeRepository zipCodeRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.rsaService = rsaService;
        this.jwtUtils = jwtUtils;
        this.zipCodeService = zipCodeService;
        this.zipCodeRepository = zipCodeRepository;
        this.customerMapper = customerMapper;
    }

    @CachePut(value = "customers", key = "#result.customerId()")
    public ViewCustomerDto createCustomer(CreateCustomerDto createCustomerDto) {
        String encryptedPassword = rsaService.encryptData(createCustomerDto.password());

        Customer customer = customerMapper.createCustomerDtoToCustomer(createCustomerDto);
        customer.setPassword(encryptedPassword);

        customerRepository.save(customer);

        return customerMapper.customerToViewCustomerDto(customer);
    }

    @Cacheable(value = "customers", key = "#id")
    public ViewCustomerDto getCustomerById(long id) {
        Customer customer = customerRepository.findFirstByCustomerId(id)
                .orElseThrow(EntityNotFoundException::customerNotFound);

        return customerMapper.customerToViewCustomerDto(customer);
    }

    @Cacheable(value = "token", key = "#loginCustomerDto.mail()")
    public String loginCustomer(LoginCustomerDto loginCustomerDto) {
        Customer customer = customerRepository.findFirstByMail(loginCustomerDto.mail().toLowerCase())
                .orElseThrow(EntityNotFoundException::customerNotFound);

        String decryptedPassword = rsaService.decryptData(customer.getPassword());

        if(!decryptedPassword.equals(loginCustomerDto.password())) throw new UnauthorizedException("Email or password incorrect");

        return jwtUtils.generateToken(customer);
    }

    public List<ViewCustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(customerMapper::customerToViewCustomerDto).toList();
    }

    public String updateCustomer(Customer customerUpdate){
        Customer customer = customerRepository.findFirstByCustomerId(
                customerUpdate.getCustomerId()
        ).orElseThrow(EntityNotFoundException::customerNotFound);

        customerRepository.save(customerUpdate);

        return "this customer has been updated";
    }

    @CacheEvict(value = "customers", allEntries = true)
    public ViewCustomerDto deleteCustomerById(long id) {
        Customer customer = customerRepository.findFirstByCustomerId(id)
                .orElseThrow(EntityNotFoundException::customerNotFound);

        customerRepository.deleteById(id);

        return customerMapper.customerToViewCustomerDto(customer);
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