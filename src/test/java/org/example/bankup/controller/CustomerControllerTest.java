package org.example.bankup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bankup.constants.CustomerRole;
import org.example.bankup.dto.customer.CreateCustomerDto;
import org.example.bankup.dto.customer.LoginCustomerDto;
import org.example.bankup.dto.customer.ViewCustomerDto;
import org.example.bankup.entity.Customer;
import org.example.bankup.repository.CustomerRepository;
import org.example.bankup.security.JwtUtils;
import org.example.bankup.security.RsaService;
import org.example.bankup.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CustomerController.class)
@ActiveProfiles("Test")
@Import(CustomerControllerTest.Config.class)
@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerService; // Agora injetado via configuração personalizada

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RsaService rsaService;

    private Customer customer;
    private CreateCustomerDto createCustomerDto;
    private LoginCustomerDto loginCustomerDto;
    private Customer customer2;
    private ViewCustomerDto viewCustomerDto;

    @BeforeEach
    void setUp() {
        customer = new Customer("Fe", "fe@m.com", "Fe",
                "anywhere", "anywhere", "anywhere", CustomerRole.ADMINISTRATOR);
        customer2 = new Customer("d", "d@m.com", "d",
                "anywhere", "anywhere", "anywhere", CustomerRole.ADMINISTRATOR);

        createCustomerDto = new CreateCustomerDto("d", "d@m.com", "d",
                "anywhere", "anywhere", "anywhere", CustomerRole.ADMINISTRATOR);

        viewCustomerDto = new ViewCustomerDto(1, "Fe", "fe@m.com", "Anywhere", "Anywhere", "Anywhere", CustomerRole.ADMINISTRATOR);

        loginCustomerDto = new LoginCustomerDto("fe@m.com", "Fe");

    }

    @Test
    @WithMockUser("spring")
    void testShouldReturn200Ok() throws Exception {

        when(customerRepository.save(any(Customer.class))).thenReturn(customer2);

        when(jwtUtils.generateToken(any(Customer.class))).thenCallRealMethod();
        when(jwtUtils.verifyToken(anyString())).thenCallRealMethod();

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(createCustomerDto);

        String token = jwtUtils.generateToken(customer);

        System.out.println(token);

        System.out.println(jwtUtils.verifyToken(token));

        mockMvc.perform(post("/customers/create")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldTakeMyCustomerByEmail() throws Exception {

        when(customerRepository.findFirstById(anyLong())).thenReturn(Optional.of(customer));

        when(customerService.getCustomerById(anyLong())).thenReturn(viewCustomerDto);

        mockMvc.perform(get("/customers/1")
        ).andDo(print()).andExpect(status().isOk());

    }

    // Definição manual dos mocks, substituindo @MockBean
    @TestConfiguration
    static class Config {
        @Bean
        public CustomerService customerService() {
            return Mockito.mock(CustomerService.class);
        }

        @Bean
        public JwtUtils jwtUtils() {
            return Mockito.mock(JwtUtils.class);
        }

        @Bean
        public CustomerRepository customerRepository() {
            return Mockito.mock(CustomerRepository.class);
        }

        @Bean
        public RsaService rsaService() {
            return Mockito.mock(RsaService.class);
        }
    }
}
