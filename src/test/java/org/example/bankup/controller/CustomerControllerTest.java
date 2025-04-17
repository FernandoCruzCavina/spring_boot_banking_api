package org.example.bankup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bankup.constants.CustomerRole;
import org.example.bankup.dto.customer.CreateCustomerDto;
import org.example.bankup.dto.customer.LoginCustomerDto;
import org.example.bankup.dto.customer.UpdateCustomerDto;
import org.example.bankup.dto.customer.ViewCustomerDto;
import org.example.bankup.entity.Customer;
import org.example.bankup.repository.CustomerRepository;
import org.example.bankup.security.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static Customer customer;
    private static CreateCustomerDto createCustomerDto;
    private static UpdateCustomerDto updateCustomerDto;
    private static LoginCustomerDto loginCustomerDto;
    private static Customer customer2;
    private static ViewCustomerDto viewCustomerDto;
    private static String token;

    @BeforeEach
    void setUpDatabase() {
        customerRepository.deleteAll();

        customer = new Customer("John", "john@m.com", "John",
                "anywhere", "anywhere", "anywhere", CustomerRole.ADMINISTRATOR);
        customer2 = new Customer("Fe", "fe@m.com", "Fe",
                "anywhere", "anywhere", "anywhere", CustomerRole.ADMINISTRATOR);

        createCustomerDto = new CreateCustomerDto("John", "john@m.com", "John",
                "anywhere", "anywhere", "anywhere", CustomerRole.ADMINISTRATOR);

        updateCustomerDto = new UpdateCustomerDto("UpdatedFe", "fe@m.com", "Fe",
                "anywhere", "anywhere", "anywhere");

        viewCustomerDto = new ViewCustomerDto(1, "John", "john@m.com", "anywhere",
                "anywhere", "anywhere", CustomerRole.ADMINISTRATOR);

        loginCustomerDto = new LoginCustomerDto("John", "john@m.com");

        customer2 = customerRepository.save(customer2);
        token = jwtUtils.generateToken(customer2);
    }

    @Test
    void shouldLoginAndReturnJwtToken() throws Exception {
        String hashedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(hashedPassword);

        customerRepository.save(customer);

        LoginCustomerDto loginDto = new LoginCustomerDto("john@m.com", "John");

        String loginDtoJson = new ObjectMapper().writeValueAsString(loginDto);

        mockMvc.perform(post("/customers/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginDtoJson))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.notNullValue()));
    }

    @Test
    void shouldCreateCustomerAndReturnViewCustomerDto() throws Exception {

        String createCustomerDtoJson = new ObjectMapper().writeValueAsString(createCustomerDto);

        mockMvc.perform(post("/customers/create")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCustomerDtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@m.com"));
    }

    @Test
    void shouldGetCustomerById() throws Exception {

        Customer customerTest = customerRepository.save(customer);

        mockMvc.perform(get("/customers/{id}", customerTest.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@m.com"));
    }

    @Test
    void shouldReturnNotFoundWhenCustomerDoesNotExist() throws Exception {

        mockMvc.perform(get("/customers/{id}", 999)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllCustomers() throws Exception {

        customerRepository.save(customer);

        mockMvc.perform(get("/customers/all")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldUpdateCustomer() throws Exception {

        mockMvc.perform(put("/customers/update")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateCustomerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedFe"));
    }

    @Test
    void shouldDeleteCustomerById() throws Exception {

        mockMvc.perform(delete("/customers/{id}", customer2.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
