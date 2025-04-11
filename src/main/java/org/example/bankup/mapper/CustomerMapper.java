package org.example.bankup.mapper;

import org.example.bankup.dto.customer.CreateCustomerDto;
import org.example.bankup.dto.customer.ViewCustomerDto;
import org.example.bankup.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    ViewCustomerDto customerToViewCustomerDto(Customer customer);
    Customer createCustomerDtoToCustomer(CreateCustomerDto customerDto);
}
