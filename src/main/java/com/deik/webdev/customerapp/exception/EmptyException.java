package com.deik.webdev.customerapp.exception;

import lombok.Data;

@Data
public class EmptyException extends Exception {

    public EmptyException(String message) {
        super(message);
    }

}
