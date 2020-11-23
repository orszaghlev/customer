package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.exception.UnknownCustomerException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.model.Customer;

import java.util.Collection;

public interface CustomerService {

    Collection<Customer> getAllCustomer();

    void recordCustomer(Customer customer) throws UnknownStaffException, UnknownCountryException;
    void deleteCustomer(Customer customer) throws UnknownCustomerException;

}
