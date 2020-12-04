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

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String country;
    private String email;
    private String store;
    private String username;
    private String password;

}
