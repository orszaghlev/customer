package com.deik.webdev.customerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StaffDto {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String country;
    private String email;
    private String store;
    private String storeAddress;
    private String storeCity;
    private String storeCountry;
    private String username;
    private String password;

}
