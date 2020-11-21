package com.deik.webdev.customerapp.exception;

import com.deik.webdev.customerapp.model.Customer;
import lombok.Data;

@Data
public class UnknownCustomerException extends Exception {

    private Customer customer;

    public UnknownCustomerException() {
    }

    public UnknownCustomerException(String message, Customer customer) {
        super(message);
        this.customer = customer;
    }

}
