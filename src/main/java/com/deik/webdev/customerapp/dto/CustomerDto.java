package com.deik.webdev.customerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private String store;
    private String staff;
    private String staffAddress;
    private String staffCity;
    private String staffCountry;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String city;
    private String country;

}
