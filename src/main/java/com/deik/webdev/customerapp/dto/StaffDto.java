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

    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private int storeId;
    private int active;
    private String username;
    private String password;

}
