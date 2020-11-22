package com.deik.webdev.customerapp.exception;

import lombok.Data;

@Data
public class UnknownStoreException extends Exception {

    public UnknownStoreException() {
    }

    public UnknownStoreException(String message) {
        super(message);
    }

}
