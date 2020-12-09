package com.deik.webdev.customerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AddressUpdateRequestDto extends AddressDto {

    private String newAddress;
    private String newAddress2;
    private String newDistrict;
    private String newCity;
    private String newCountry;
    private String newPostalCode;
    private String newPhone;

}
