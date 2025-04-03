package org.example.bankup.mapper;

import org.example.bankup.dto.customer.CreateCustomerDto;
import org.example.bankup.dto.customer.ViewCustomerDto;
import org.example.bankup.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    static CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    ViewCustomerDto customerToViewCustomerDto(Customer customer);
    Customer createCustomerDtoToCustomer(CreateCustomerDto customerDto);
}
