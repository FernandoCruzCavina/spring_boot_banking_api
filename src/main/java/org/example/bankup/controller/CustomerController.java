package org.example.bankup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.bankup.dto.customer.CreateCustomerDto;
import org.example.bankup.dto.customer.LoginCustomerDto;
import org.example.bankup.dto.customer.ViewCustomerDto;
import org.example.bankup.entity.Customer;
import org.example.bankup.entity.ZipCode;
import org.example.bankup.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewCustomerDto> getCustomerDtoById(@PathVariable int id) {
        ViewCustomerDto viewCustomerDto = customerService.getCustomerById(id);
        if (viewCustomerDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(viewCustomerDto);
    }

    @Operation(
            description = "authentication",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "admin login",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginCustomerDto.class),
                            examples = @ExampleObject(
                                    name = "admin",
                                    value = "{\"mail\": \"fe@m.com\"," +
                                            "\"password\": \"Fe\"}",
                                    description = "all access"
                            )
                    )
            )
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            examples = {@ExampleObject(
                                    value = "your JWT token"
                            )}
                    )
            )
    )
    @PostMapping("/login")
    public ResponseEntity<String> loginCustomer(@RequestBody LoginCustomerDto loginCustomerDto) {
        String token = customerService.loginCustomer(loginCustomerDto);
        if (token == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(token);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCustomerDto(@RequestBody CreateCustomerDto createCustomerDto) {
        Customer customer = customerService.createCustomer(createCustomerDto);

        return ResponseEntity.ok("Customer created!\n"+ customer);

    }

    @GetMapping("/all")
    public List<ViewCustomerDto> getAllCustomers() {

        return customerService.getAllCustomers();
    }

    @PutMapping("/update")
    public String updateCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable int id) {
        ViewCustomerDto viewCustomerDto = customerService.getCustomerById(id);
        if (viewCustomerDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(viewCustomerDto);
    }

    @GetMapping("/{country}/{postalCode}")
    public ResponseEntity<ZipCode> getZipCodeByPostalCode(@PathVariable String country, @PathVariable String postalCode) {
        return ResponseEntity.ok(customerService.zipCode(country, postalCode));
    }
}
