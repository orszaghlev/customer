package com.deik.webdev.customerapp.exception;

import com.deik.webdev.customerapp.model.Country;
import lombok.Data;

@Data
public class UnknownCountryException extends Exception {

    private Country country;

    public UnknownCountryException() {
    }

    public UnknownCountryException(String message, Country country) {
        super(message);
        this.country = country;
    }

}

