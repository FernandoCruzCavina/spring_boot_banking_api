package org.example.bankup.service;

import org.example.bankup.constants.CustomerRole;
import org.example.bankup.dto.customer.CreateCustomerDto;
import org.example.bankup.dto.customer.LoginCustomerDto;
import org.example.bankup.dto.customer.UpdateCustomerDto;
import org.example.bankup.dto.customer.ViewCustomerDto;
import org.example.bankup.entity.Customer;
import org.example.bankup.entity.ZipCode;
import org.example.bankup.mapper.CustomerMapper;
import org.example.bankup.repository.CustomerRepository;
import org.example.bankup.repository.ZipCodeRepository;
import org.example.bankup.security.JwtUtils;
import org.example.bankup.security.RsaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private ZipCodeService zipCodeService;
    @Mock
    private ZipCodeRepository zipCodeRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RsaService rsaService;

    @InjectMocks
    private CustomerService customerService;

    private static Customer customer;
    private static CreateCustomerDto createCustomerDto;
    private static ViewCustomerDto viewCustomerDto;
    private static UpdateCustomerDto updateCustomerDto;
    private static LoginCustomerDto loginCustomerDto;

    @BeforeAll
    static void setUp() {
        customer = new Customer(1,"John", "john@m.com", "John",
                "Brazil", "SP", "sp", CustomerRole.STANDARD_CUSTOMER);
        createCustomerDto = new CreateCustomerDto("John", "john@m.com", "John",
                "Brazil", "SP", "sp", CustomerRole.STANDARD_CUSTOMER);
        viewCustomerDto = new ViewCustomerDto(1, "John", "john@m.com", "John",
                "Brazil", "SP", CustomerRole.STANDARD_CUSTOMER);
        updateCustomerDto = new UpdateCustomerDto("John", "john@m.com", "John",
                "Brazil", "SP", "sp");
        loginCustomerDto = new LoginCustomerDto("john@m.com", "John");
    }

    @Test
    void findCustomerByIdShouldReturnViewCustomerDto() {

        when(customerRepository.findFirstById(1)).thenReturn(Optional.of(customer));
        when(customerMapper.customerToViewCustomerDto(customer)).thenReturn(viewCustomerDto);

        ViewCustomerDto result = customerService.getCustomerById(1);

        assertNotNull(result);
        assertEquals(customer.getName(), result.name());
        assertEquals(customer.getEmail(), result.email());
    }

    @Test
    void createCustomerShouldReturnViewCustomerDto() {

        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.createCustomerDtoToCustomer(createCustomerDto)).thenReturn(customer);
        when(customerMapper.customerToViewCustomerDto(customer)).thenReturn(viewCustomerDto);
        when(passwordEncoder.encode(createCustomerDto.password())).thenReturn("hashedPassword");

        ViewCustomerDto result = customerService.createCustomer(createCustomerDto);

        assertNotNull(result);
        assertEquals(customer.getName(), result.name());
        assertEquals(customer.getEmail(), result.email());
    }

    @Test
    void updateCustomerShouldReturnViewCustomerDto() {

        when(customerRepository.findFirstByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        when(customerMapper.customerToViewCustomerDto(customer)).thenReturn(viewCustomerDto);
        when(passwordEncoder.encode(createCustomerDto.password())).thenReturn("hashedPassword");

        ViewCustomerDto result = customerService.updateCustomer(updateCustomerDto);

        assertNotNull(result);
        assertEquals(customer.getName(), result.name());
        assertEquals(customer.getEmail(), result.email());
    }

    @Test
    void deleteCustomerByIdShouldReturnViewCustomerDto() {
        when(customerRepository.findFirstById(customer.getId())).thenReturn(Optional.of(customer));
        when(customerMapper.customerToViewCustomerDto(customer)).thenReturn(viewCustomerDto);

        ViewCustomerDto result = customerService.deleteCustomerById(1);

        assertNotNull(result);
        assertEquals(customer.getName(), result.name());
        assertEquals(customer.getEmail(), result.email());
    }

    @Test
    void loginShouldReturnToken() {
        when(customerRepository.findFirstByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        when(passwordEncoder.matches(loginCustomerDto.password(), customer.getPassword())).thenReturn(true);
        when(jwtUtils.generateToken(customer)).thenReturn("token");

        String tokenResult = customerService.loginCustomer(loginCustomerDto);

        assertNotNull(tokenResult);
        assertEquals("token", tokenResult);
    }

}