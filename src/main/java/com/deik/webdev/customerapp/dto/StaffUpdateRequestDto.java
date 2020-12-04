package com.deik.webdev.customerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StaffUpdateRequestDto extends StaffDto {

    private String newFirstName;
    private String newLastName;
    private String newAddress;
    private String newCity;
    private String newCountry;
    private String newEmail;
    private String newStore;
    private String newUsername;
    private String newPassword;

}
