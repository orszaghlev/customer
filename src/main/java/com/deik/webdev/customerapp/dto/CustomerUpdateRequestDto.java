package com.deik.webdev.customerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateRequestDto extends CustomerDto {

    private int newStoreId;
    private String newFirstName;
    private String newLastName;
    private String newEmail;
    private int newAddressId;
    private int newActive;

}
