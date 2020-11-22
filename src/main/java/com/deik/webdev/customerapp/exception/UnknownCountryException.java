package com.deik.webdev.customerapp.exception;

import com.deik.webdev.customerapp.model.Country;
import lombok.Data;

@Data
public class UnknownCountryException extends Exception {

    public UnknownCountryException() {
    }

    public UnknownCountryException(String message) {
        super(message);
    }

}

