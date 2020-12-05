package com.deik.webdev.customerapp.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class Staff {

    private int id;
    private String firstName;
    private String lastName;
    private int addressId;
    private String email;
    private int storeId;
    private int active;
    private String username;
    private String password;

}
