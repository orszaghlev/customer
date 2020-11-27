package com.deik.webdev.customerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {

    private String staff;
    private String staffAddress;
    private String staffCity;
    private String staffCountry;
    private String address;
    private String city;
    private String country;

}
