package com.deik.webdev.customerapp.exception;

import com.deik.webdev.customerapp.model.City;
import lombok.Data;

@Data
public class UnknownCityException extends Exception {

    private City city;

    public UnknownCityException(City city) {
        this.city = city;
    }

    public UnknownCityException(String message, City city) {
        super(message);
        this.city = city;
    }

    public UnknownCityException(String city) {
        super(city);
    }

}
