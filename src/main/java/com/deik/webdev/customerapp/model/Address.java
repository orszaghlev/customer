package com.deik.webdev.customerapp.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class Address {

    private int id;
    private String address;
    private String address2;
    private String district;
    private String city;
    private String postalCode;
    private String phone;

}
