package org.example.bankup.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.example.bankup.constants.CustomerRole;

@Builder
@Data
@AllArgsConstructor
@Getter

public class ViewCustomerDto {

    private long id;
    private String name;
    private String mail;
    private String country;
    private String state;
    private String city;
    private CustomerRole customerRole;
}
