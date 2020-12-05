package com.deik.webdev.customerapp.exception;

import lombok.Data;

@Data
public class OutOfBoundsException extends Exception {

    private int value;

    public OutOfBoundsException(String message) {
        super(message);
    }

}
