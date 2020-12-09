package com.deik.webdev.customerapp.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CityUpdateRequestDto extends CityDto {

    private String newCity;
    private int newCountryId;

}
