package com.deik.webdev.customerapp.exception;

import com.deik.webdev.customerapp.model.City;
import lombok.Data;

@Data
public class UnknownCityException extends Exception {

    private City city;

    public UnknownCityException() {
    }

    public UnknownCityException(String message, City city) {
        super(message);
        this.city = city;
    }

}
