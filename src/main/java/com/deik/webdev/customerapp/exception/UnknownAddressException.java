package com.deik.webdev.customerapp.exception;

import com.deik.webdev.customerapp.model.Address;
import lombok.Data;

@Data
public class UnknownAddressException extends Exception {

    private Address address;

    public UnknownAddressException() {
    }

    public UnknownAddressException(String message, Address address) {
        super(message);
        this.address = address;
    }

}
