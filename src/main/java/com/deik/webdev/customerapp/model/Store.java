package com.deik.webdev.customerapp.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class Store {

    private String id;

    @ToString.Exclude
    private String staff;

    private String staffAddress;
    private String staffCity;
    private String staffCountry;
    private String address;
    private String city;
    private String country;

}
