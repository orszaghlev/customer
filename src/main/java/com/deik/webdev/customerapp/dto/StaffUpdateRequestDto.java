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
    private int newAddressId;
    private String newEmail;
    private int newStoreId;
    private int newActive;
    private String newUsername;
    private String newPassword;

}
