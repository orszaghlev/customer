package com.deik.webdev.customerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {

    private String id;
    private String staff;
    private String address;
    private String city;
    private String country;

}
