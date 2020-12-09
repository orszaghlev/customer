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

    private int id;
    private int storeId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private int active;

}
