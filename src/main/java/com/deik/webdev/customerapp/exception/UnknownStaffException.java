package com.deik.webdev.customerapp.exception;

import lombok.Data;

@Data
public class UnknownStaffException extends Exception {

    public UnknownStaffException() {
    }

    public UnknownStaffException(String message) {
        super(message);
    }

}
